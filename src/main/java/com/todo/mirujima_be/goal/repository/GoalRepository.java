package com.todo.mirujima_be.goal.repository;

import com.todo.mirujima_be.goal.entity.Goal;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

  int countByCreatedByEmailAndIdLessThanOrderByIdDesc(String email, Long id);

  List<Goal> findAllByCreatedByEmailAndIdLessThanOrderByIdDesc(String email, Long id, Pageable pageable);

}
