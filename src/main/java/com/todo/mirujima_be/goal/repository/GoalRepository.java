package com.todo.mirujima_be.goal.repository;

import com.todo.mirujima_be.goal.entity.Goal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    @EntityGraph(attributePaths = "user")
    List<Goal> findAllByCreatedByIdAndIdLessThanOrderByIdDesc(Long userId, Long id, Pageable pageable);

}
