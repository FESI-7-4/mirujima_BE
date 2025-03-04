package com.todo.mirujima_be.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {

//    private final RedisCacheManager cacheManager;
//    private final RedisTemplate<String, Object> redisTemplate;

//    public void evictCachesByPattern(String cacheName, String prefix) {
//        var options = ScanOptions.scanOptions()
//                .match(cacheName + "::" + prefix + "::*")
//                .count(100)
//                .build();
//        try (
//                Cursor<byte[]> cursor = redisTemplate.executeWithStickyConnection(
//                        redisConnection -> redisConnection.scan(options)
//                )
//        ) {
//            while (cursor.hasNext()) {
//                var key = new String(cursor.next()).split(cacheName + "::")[1];
//                cacheManager.getCache(cacheName).evict(key);
//            }
//        }
//    }

//    public void evictCacheByKey(String cacheName, String key) {
//        cacheManager.getCache(cacheName).evict(key);
//    }
}
