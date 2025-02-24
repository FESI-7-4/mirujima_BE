package com.todo.mirujima_be.goal.service;

import static com.todo.mirujima_be.auth.util.AuthUtil.checkAuthority;

import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.contant.MirujimaConstants;
import com.todo.mirujima_be.common.exception.AlertException;
import com.todo.mirujima_be.goal.dto.GoalPageCollection;
import com.todo.mirujima_be.goal.dto.request.GoalListRequest;
import com.todo.mirujima_be.goal.dto.request.GoalModRequest;
import com.todo.mirujima_be.goal.dto.request.GoalRegRequest;
import com.todo.mirujima_be.goal.dto.response.GoalResponse;
import com.todo.mirujima_be.goal.entity.Goal;
import com.todo.mirujima_be.goal.repository.GoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class GoalService {

  private final GoalRepository goalRepository;

  public GoalResponse getGoal(Long id) {
    var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
    checkAuthority(MirujimaConstants.Goal.Goal, goal);
    return GoalResponse.of(goal);
  }

  public GoalPageCollection getGoalList(GoalListRequest goalListRequest) {
    var pageSize = goalListRequest.getPageSize();
    var lastSeenId = goalListRequest.getLastSeenId();
    if (pageSize == null) {
      pageSize = MirujimaConstants.Goal.PAGE_SIZE;
    }
    var pageable = PageRequest.of(0, pageSize);
    var email = AuthUtil.getEmail();
    var goalCount = goalRepository.countByCreatedByEmailAndIdLessThanOrderByIdDesc(email, lastSeenId);
    var goals = goalRepository.findAllByCreatedByEmailAndIdLessThanOrderByIdDesc(email, lastSeenId, pageable);
    var lastSeenGoalId = goals.stream().mapToLong(Goal::getId).min().orElse(0L);
    return GoalPageCollection.builder()
        .lastSeenId(lastSeenGoalId)
        .remainingCount(goalCount - goals.size())
        .goals(goals.stream().map(GoalResponse::of).toList())
        .build();
  }

  @Transactional
  public GoalResponse createGoal(GoalRegRequest goalRegRequest) {
    var goal = Goal.builder()
        .title(goalRegRequest.getTitle())
        .completionDate(goalRegRequest.getCompletionDate().atStartOfDay())
        .build();
    goalRepository.save(goal);
    return GoalResponse.of(goal);
  }

  @Transactional
  public GoalResponse updateGoal(Long id, GoalModRequest goalModRequest) {
    var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
    checkAuthority(MirujimaConstants.Goal.Goal, goal);
    goal.setTitle(goalModRequest.getTitle());
    goal.setCompletionDate(goalModRequest.getCompletionDate());
    return GoalResponse.of(goal);
  }

  @Transactional
  public void deleteGoal(Long id) {
    var goal = goalRepository.findById(id).orElseThrow(() -> new AlertException("목표가 존재하지 않습니다"));
    checkAuthority(MirujimaConstants.Goal.Goal, goal);
    goalRepository.deleteById(id);
  }

}
