package com.todo.mirujima_be.goal.repository;

import com.todo.mirujima_be.goal.entity.Goal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

  int countByCreatedByEmailAndIdLessThanOrderByIdDesc(String email, Long id);

  @EntityGraph(attributePaths = {"createdBy"})
  List<Goal> findAllByCreatedByEmailAndIdLessThanOrderByIdDesc(String email, Long id, Pageable pageable);

  @EntityGraph(attributePaths = {"createdBy"})
  Optional<Goal> findById(Long id);

  @Modifying
  @Query("DELETE FROM Goal g WHERE g.id = :goalId")
  void deleteById(@Param("goalId") Long goalId);

}
