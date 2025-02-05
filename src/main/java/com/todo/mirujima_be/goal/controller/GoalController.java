package com.todo.mirujima_be.goal.controller;

import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.goal.dto.PageCollection;
import com.todo.mirujima_be.goal.dto.request.CreateGoalRequest;
import com.todo.mirujima_be.goal.dto.request.UpdateGoalRequest;
import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import com.todo.mirujima_be.goal.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Goal", description = "목표 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/mirujima/goal")
@RestController
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    @Operation(summary = "목표 생성 API", description = "목표를 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> createGoal(@RequestBody CreateGoalRequest createGoalRequest) {
        var goalResponse = goalService.createGoal(createGoalRequest);
        // 목표 생성 로직
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "목표 조회 API", description = "목표를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> getGoal(@PathVariable Long id) {
        var goalResponse = goalService.getGoal(id);
        // 목표 조회 로직표
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @GetMapping("")
    @Operation(summary = "목표 목록 조회 API", description = "목표 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<PageCollection<GoalResponse>> getGoalList(@RequestParam Long lastSeenId) {
        var goalResponseList = goalService.getGoalList(lastSeenId);
        // 목표 목록 조회 로직
        return new CommonResponse<PageCollection<GoalResponse>>()
                .success(goalResponseList);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "목표 수정 API", description = "목표을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> updateGoal(@PathVariable Long id, @RequestBody UpdateGoalRequest updateGoalRequest) {
        var goalResponse = goalService.updateGoal(id, updateGoalRequest);
        // 목표 수정 로직
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "목표 삭제 API", description = "목표을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<?> deleteGoal(@PathVariable Long id) {
        // 목표 삭제 로직
        goalService.deleteGoal(id);
        return new CommonResponse<>().success("목표가 삭제되었습니다.");
    }

}
