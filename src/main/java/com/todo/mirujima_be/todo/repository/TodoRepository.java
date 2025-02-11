package com.todo.mirujima_be.todo.repository;

import com.todo.mirujima_be.todo.entity.Todo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>, CustomTodoRepository {

    List<Todo> findAllByGoalIdAndDoneAndIdLessThanOrderByIdDesc(Long goalId, boolean done, Long id, Pageable pageable);

    List<Todo> findAllByCreatedById(Long id);

}
