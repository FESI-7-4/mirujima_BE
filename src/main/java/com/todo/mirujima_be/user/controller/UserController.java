package com.todo.mirujima_be.user.controller;

import com.todo.mirujima_be.auth.dto.response.EmailCheckResponse;
import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.user.dto.request.ModificationRequest;
import com.todo.mirujima_be.user.dto.request.RegisterRequest;
import com.todo.mirujima_be.user.dto.response.UserResponse;
import com.todo.mirujima_be.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "유저 정보 조회 API")
@RequestMapping("/mirujima/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입 API", description = "유저네임, 이메일, 비밀번호를 받아 회원가입을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "회원가입 성공"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패")
            }
    )
    public CommonResponse<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        var response = userService.register(registerRequest);
        return new CommonResponse<UserResponse>().success(response);
    }

    @PutMapping
    @Operation(summary = "유저 정보 수정 API", description = "유저네임, 이메일, 비밀번호를 받아 수정을 진행합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "유저 정보 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "유저 정보 수정 실패")
            }
    )
    public CommonResponse<UserResponse> modify(@RequestBody ModificationRequest registerRequest) {
        var response = userService.modify(registerRequest);
        return new CommonResponse<UserResponse>().success(response);
    }

    @GetMapping
    @Operation(summary = "유저 정보 조회 API", description = "토큰을 활용해 유저 정보를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "유저 정보 조회 성공."),
                    @ApiResponse(responseCode = "400",
                            description = "유저 정보 조회 실패")
            }
    )
    public UserResponse getUserInfo() {
        return userService.getUserInfo();
    }

    @GetMapping("/exists")
    @Operation(summary = "이메일 중복 검사 API", description = "이메일을 받아서 중복인지 검사한다.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "이메일 중복 검사 성공."),
                    @ApiResponse(responseCode = "400",
                            description = "이메일 검사 실패. 잘못된 이메일 형식이거나 서버 오류로 인해 검사가 실패했습니다.")
            }
    )
    public CommonResponse<EmailCheckResponse> checkEmailExists(@RequestParam String email) {
        return new CommonResponse<EmailCheckResponse>().success(userService.checkEmailExists(email));
    }
}
