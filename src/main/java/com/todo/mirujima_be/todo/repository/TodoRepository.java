package com.todo.mirujima_be.todo.repository;

import com.todo.mirujima_be.todo.entity.Todo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TodoRepository extends JpaRepository<Todo, Long>, CustomTodoRepository {

  @EntityGraph(attributePaths = {"note"})
  List<Todo> findAllByCreatedByEmail(String email);

  @Modifying
  @Query("DELETE FROM Todo t WHERE t.goal.id = :goalId")
  void deleteByGoalId(@Param("goalId") Long goalId);

  @EntityGraph(attributePaths = {"goal", "createdBy", "note"})
  List<Todo> findAllByIdIn(Iterable<Long> ids);

  @EntityGraph(attributePaths = {"goal", "createdBy", "note"})
  Optional<Todo> findById(Long id);

}
