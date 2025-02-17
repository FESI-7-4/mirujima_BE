package com.todo.mirujima_be.goal.dto;

import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GoalPageCollection {

  private Long lastSeenId;
  private Integer remainingCount;
  private List<GoalResponse> goals;

}
