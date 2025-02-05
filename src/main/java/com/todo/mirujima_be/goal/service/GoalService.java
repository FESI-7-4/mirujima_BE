package com.todo.mirujima_be.goal.service;

import com.todo.mirujima_be.goal.dto.PageCollection;
import com.todo.mirujima_be.goal.dto.request.CreateGoalRequest;
import com.todo.mirujima_be.goal.dto.request.UpdateGoalRequest;
import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import com.todo.mirujima_be.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalResponse createGoal(CreateGoalRequest createGoalRequest) {
        return null;
    }

    @Transactional(readOnly = true)
    public GoalResponse getGoal(Long id) {
        return null;
    }

    public void deleteGoal(Long id) {
    }

    @Transactional(readOnly = true)
    public PageCollection<GoalResponse> getGoalList(Long lastSeenId) {
        return null;
    }

    public GoalResponse updateGoal(Long id, UpdateGoalRequest updateGoalRequest) {
        return null;
    }

}
