package com.todo.mirujima_be.todo.dto;

import com.todo.mirujima_be.todo.dto.response.TodoResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TodoPageCollection {

  private Long lastSeenId;
  private Integer remainingCount;
  private List<TodoResponse> todos;

}
