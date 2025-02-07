package com.todo.mirujima_be.goal.controller;

import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.goal.dto.GoalPageCollection;
import com.todo.mirujima_be.goal.dto.request.GoalListRequest;
import com.todo.mirujima_be.goal.dto.request.GoalModRequest;
import com.todo.mirujima_be.goal.dto.request.GoalRegRequest;
import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import com.todo.mirujima_be.goal.service.GoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Goal", description = "목표 관련 API")
@RequiredArgsConstructor
@RequestMapping("/mirujima/goals")
@RestController
public class GoalController {

    private final GoalService goalService;

    @GetMapping
    @Operation(summary = "목표 목록 조회 API", description = "목표 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalPageCollection> getGoalList(@ModelAttribute GoalListRequest goalListRequest) {
        var goalResponseList = goalService.getGoalList(goalListRequest);
        // 목표 목록 조회 로직
        return new CommonResponse<GoalPageCollection>().success(goalResponseList);
    }

    @PostMapping
    @Operation(summary = "목표 생성 API", description = "목표를 생성합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> createGoal(@RequestBody GoalRegRequest goalRegRequest) {
        var goalResponse = goalService.createGoal(goalRegRequest);
        // 목표 생성 로직
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @GetMapping("/{goalId}")
    @Operation(summary = "목표 조회 API", description = "목표를 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> getGoal(@PathVariable(name = "goalId") Long id) {
        var goalResponse = goalService.getGoal(id);
        // 목표 조회 로직표
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @PatchMapping("/{goalId}")
    @Operation(summary = "목표 수정 API", description = "목표을 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<GoalResponse> updateGoal(
            @PathVariable(name = "goalId") Long id, @RequestBody GoalModRequest goalModRequest
    ) {
        var goalResponse = goalService.updateGoal(id, goalModRequest);
        // 목표 수정 로직
        return new CommonResponse<GoalResponse>().success(goalResponse);
    }

    @DeleteMapping("/{goalId}")
    @Operation(summary = "목표 삭제 API", description = "목표을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "실패")
            }
    )
    public CommonResponse<?> deleteGoal(@PathVariable(name = "goalId") Long id) {
        // 목표 삭제 로직
        goalService.deleteGoal(id);
        return new CommonResponse<>().success(null);
    }

}
