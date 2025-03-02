package com.todo.mirujima_be.goal.dto;

import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalPageCollection {

    private Long lastSeenId;
    private Integer remainingCount;
    private List<GoalResponse> goals;

}
