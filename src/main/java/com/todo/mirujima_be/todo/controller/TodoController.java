package com.todo.mirujima_be.todo.controller;

import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import com.todo.mirujima_be.todo.dto.response.TodoDetailResponse;
import com.todo.mirujima_be.todo.dto.response.TodoProceedStatusResponse;
import com.todo.mirujima_be.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mirujima/todo")
@Tag(name = "Todo", description = "투두 API")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "투두 목록 조회 API", description = "목표에 대한 투두 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<List<TodoDetailResponse>> getTodoList(@ModelAttribute TodoListRequest todoListRequest) {
        var todoDetailDtoList = todoService.getTodoList(todoListRequest);
        return new CommonResponse<List<TodoDetailResponse>>().success(todoDetailDtoList);
    }

    @PostMapping
    @Operation(summary = "투두 등록 API", description = "목표에 대한 투두를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerTodo(@RequestBody @Valid TodoRegRequest todoRegRequest) {
        var todoId = todoService.registerTodo(todoRegRequest);
        return new CommonResponse<Long>().success(todoId);
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "투두 상세 조회 API", description = "목표에 대한 투두를 상세 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoDetailResponse> getTodoDetail(@PathVariable("todoId") long todoId) {
        var todoDetailDto = todoService.getTodoDetail(todoId);
        return new CommonResponse<TodoDetailResponse>().success(todoDetailDto);
    }

    @PutMapping
    @Operation(summary = "투두 수정 API", description = "목표에 대한 투두를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyTodo(@RequestBody @Valid TodoModRequest todoModRequest) {
        var result = todoService.modifyTodo(todoModRequest);
        return new CommonResponse<Long>().success(result);
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "투두 삭제 API", description = "목표에 대한 투두를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Map<String, Long>> deleteTodo(@PathVariable("todoId") long todoId) {
        todoService.deleteTodo(todoId);
        return new CommonResponse<Map<String, Long>>().success(Map.of("todoId", todoId));
    }

    @GetMapping("/progress")
    @Operation(summary = "투두 진행도 조회 API", description = "내 투두에 대한 진행도를 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoProceedStatusResponse> getProgressStatus() {
        var todoProceedStatusDto = todoService.getProgressStatus();
        return new CommonResponse<TodoProceedStatusResponse>().success(todoProceedStatusDto);
    }

    @PutMapping("/{todoId}")
    @Operation(summary = "투두 완료 API", description = "투두 완료 처리",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> completeTodo(@PathVariable("todoId") long todoId, @RequestParam(name = "isCompleted") boolean isCompleted) {
        todoService.completeTodo(todoId, isCompleted);
        return new CommonResponse<>().success(true);
    }

}
