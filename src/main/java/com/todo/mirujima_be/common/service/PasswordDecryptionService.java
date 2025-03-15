package com.todo.mirujima_be.common.service;

import com.todo.mirujima_be.common.exception.AlertException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Component
public class PasswordDecryptionService {

    private static final int POOL_SIZE = 10; // 풀 크기 설정
    private static final long TIMEOUT_SECONDS = 5; // 최대 대기 시간

    private final ConcurrentLinkedQueue<Cipher> cipherPool = new ConcurrentLinkedQueue<>();
    private Semaphore semaphore;

    @Value("${password.secret-key}")
    private String secretKey; // 시크릿 키

    @PostConstruct
    private void init() {
        // 세마포어 초기화 - 동시에 POOL_SIZE 만큼의 Cipher 객체 사용 가능
        this.semaphore = new Semaphore(POOL_SIZE, true); // fair 모드 활성화
        // 풀에 Cipher 객체 미리 생성
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                cipherPool.add(Cipher.getInstance("AES/CBC/PKCS5Padding"));
            } catch (Exception e) {
                throw new RuntimeException("Cipher 초기화 실패", e);
            }
        }
    }

    public String decryptPassword(String encryptedData) {
        Cipher cipher = null;
        boolean acquired = false;
        try {
            // 세마포어 획득 시도 (타임아웃 설정)
            acquired = semaphore.tryAcquire(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            if (!acquired) throw new AlertException("서버 부하가 높습니다. 잠시 후 다시 시도해주세요.");

            // 풀에서 Cipher 객체 가져오기
            cipher = cipherPool.poll();

            // 클라이언트에서 전송된 데이터에서 암호문과 IV 분리 (구분자 ':' 사용)
            var parts = encryptedData.split(":");
            if (parts.length != 2) throw new AlertException("잘못된 형식의 암호화 데이터입니다.");
            var encryptedPassword = parts[0];
            var ivString = parts[1];

            // Base64 디코딩
            var encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            var ivBytes = Base64.getDecoder().decode(ivString);

            // 시크릿 키 생성
            var secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            // IV 설정
            var ivParameterSpec = new IvParameterSpec(ivBytes);
            // Cipher 객체 초기화
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 복호화 수행
            var decryptedBytes = cipher.doFinal(encryptedBytes);
            // 결과 반환
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (AlertException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 복호화 중 오류가 발생했습니다.", e);
        } finally {
            if (cipher != null) cipherPool.offer(cipher);
            if (acquired) semaphore.release();
        }
    }

}
