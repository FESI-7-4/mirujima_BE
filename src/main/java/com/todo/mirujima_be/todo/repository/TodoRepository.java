package com.todo.mirujima_be.todo.repository;

import com.todo.mirujima_be.todo.entity.Todo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>, CustomTodoRepository {

  List<Todo> findAllByCreatedByEmail(String email);

  void deleteByGoalId(Long goalId);

}
