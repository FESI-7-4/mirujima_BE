package com.todo.mirujima_be.goal.dto;

import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GoalPageCollection {

    private Long lastSeenId;
    private Integer totalCount;
    private List<GoalResponse> goals;

}
