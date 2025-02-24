package com.todo.mirujima_be.todo.controller;

import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.todo.dto.TodoPageCollection;
import com.todo.mirujima_be.todo.dto.request.TodoCompletionRequest;
import com.todo.mirujima_be.todo.dto.request.TodoListRequest;
import com.todo.mirujima_be.todo.dto.request.TodoModRequest;
import com.todo.mirujima_be.todo.dto.request.TodoRegRequest;
import com.todo.mirujima_be.todo.dto.response.TodoProceedStatusResponse;
import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import com.todo.mirujima_be.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mirujima/todos")
@Tag(name = "Todo", description = "할일 API")
public class TodoController {

    private final TodoService todoService;

    @GetMapping
    @Operation(summary = "할일 목록 조회 API", description = "목표에 대한 할일 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoPageCollection> getTodoList(@ModelAttribute TodoListRequest todoListRequest) {
        var response = todoService.getTodoList(todoListRequest);
        return new CommonResponse<TodoPageCollection>().success(response);
    }

    @PostMapping
    @Operation(summary = "할일 등록 API", description = "목표에 대한 할일를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoResponse> registerTodo(@RequestBody @Valid TodoRegRequest todoRegRequest) {
        var response = todoService.registerTodo(todoRegRequest);
        return new CommonResponse<TodoResponse>().success(response);
    }

    @GetMapping("/{todoId}")
    @Operation(summary = "할일 상세 조회 API", description = "목표에 대한 할일를 상세 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoResponse> getTodoDetail(@PathVariable("todoId") long id) {
        var todoDetailDto = todoService.getTodoDetail(id);
        return new CommonResponse<TodoResponse>().success(todoDetailDto);
    }

    @PatchMapping("/{todoId}")
    @Operation(summary = "할일 수정 API", description = "목표에 대한 할일를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoResponse> modifyTodo(
            @PathVariable(name = "todoId") Long id, @RequestBody @Valid TodoModRequest todoModRequest
    ) {
        var response = todoService.modifyTodo(id, todoModRequest);
        return new CommonResponse<TodoResponse>().success(response);
    }

    @PatchMapping("/completion/{todoId}")
    @Operation(summary = "할일 완료 API", description = "목표에 대한 할일을 완료합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoResponse> completeTodo(
            @PathVariable(name = "todoId") Long id, @RequestBody @Valid TodoCompletionRequest todoCompletionRequest
    ) {
        var response = todoService.completeTodo(id, todoCompletionRequest);
        return new CommonResponse<TodoResponse>().success(response);
    }

    @DeleteMapping("/{todoId}")
    @Operation(summary = "할일 삭제 API", description = "목표에 대한 할일를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> deleteTodo(@PathVariable("todoId") long todoId) {
        todoService.deleteTodo(todoId);
        return new CommonResponse<>().success(null);
    }

    @GetMapping("/progress")
    @Operation(summary = "할일 진행도 조회 API", description = "내 할일에 대한 진행도를 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<TodoProceedStatusResponse> getProgressStatus() {
        var todoProceedStatusDto = todoService.getProgressStatus();
        return new CommonResponse<TodoProceedStatusResponse>().success(todoProceedStatusDto);
    }

}
