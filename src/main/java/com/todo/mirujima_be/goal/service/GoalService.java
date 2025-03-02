package com.todo.mirujima_be.goal.service;

import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.common.service.CacheService;
import com.todo.mirujima_be.goal.dto.GoalPageCollection;
import com.todo.mirujima_be.goal.dto.request.GoalListRequest;
import com.todo.mirujima_be.goal.dto.request.GoalModRequest;
import com.todo.mirujima_be.goal.dto.request.GoalRegRequest;
import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.goal.repository.GoalRepository;
import com.todo.mirujima_be.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.todo.mirujima_be.auth.util.AuthUtil.checkAuthority;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GoalService {

    private final CacheService cacheService;

    private final GoalRepository goalRepository;
    private final TodoRepository todoRepository;

    @Cacheable(
            value = "goalList",
            key = "#email + '::' + #goalListRequest.lastSeenId + '::' + #goalListRequest.pageSize"
    )
    public GoalPageCollection getGoalList(String email, GoalListRequest goalListRequest) {
        var pageSize = goalListRequest.getPageSize();
        var lastSeenId = goalListRequest.getLastSeenId();
        if (pageSize == null) {
            pageSize = MirujimaConstants.Goal.PAGE_SIZE;
        }
        var pageable = PageRequest.of(0, pageSize);
        var goalCount = goalRepository.countByCreatedByEmailAndIdLessThanOrderByIdDesc(email, lastSeenId);
        var goals = goalRepository.findAllByCreatedByEmailAndIdLessThanOrderByIdDesc(email, lastSeenId, pageable);
        var lastSeenGoalId = goals.stream().mapToLong(Goal::getId).min().orElse(0L);
        return GoalPageCollection.builder()
                .lastSeenId(lastSeenGoalId)
                .remainingCount(goalCount - goals.size())
                .goals(goals.stream().map(GoalResponse::of).toList())
                .build();
    }

    @Cacheable(value = "goal", key = "#email + '::'+ #id")
    public GoalResponse getGoalDetail(String email, Long id) {
        var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
        checkAuthority(email, MirujimaConstants.Goal.Goal, goal);
        return GoalResponse.of(goal);
    }

    @Transactional
    public GoalResponse createGoal(String email, GoalRegRequest goalRegRequest) {
        var goal = Goal.builder()
                .title(goalRegRequest.getTitle())
                .completionDate(goalRegRequest.getCompletionDate().atStartOfDay())
                .build();
        goalRepository.save(goal);

        evictGoalListCache(email);
        return GoalResponse.of(goal);
    }

    @Transactional
    @CachePut(value = "goal", key = "#email + '::' + #id")
    public GoalResponse modifyGoal(String email, Long id, GoalModRequest goalModRequest) {
        var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
        checkAuthority(email, MirujimaConstants.Goal.Goal, goal);
        goal.setTitle(goalModRequest.getTitle());
        goal.setCompletionDate(goalModRequest.getCompletionDate());

        evictGoalListCache(email);
        return GoalResponse.of(goal);
    }

    @Transactional
    @CacheEvict(value = "goal", key = "#email + '::' + #id")
    public void deleteGoal(String email, Long id) {
        var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
        checkAuthority(email, MirujimaConstants.Goal.Goal, goal);
        todoRepository.deleteByGoalId(id);
        goalRepository.deleteById(id);

        evictGoalListCache(email);
    }

    private void evictGoalListCache(String email) {
        cacheService.evictCachesByPattern("goalList", email);
    }

}
