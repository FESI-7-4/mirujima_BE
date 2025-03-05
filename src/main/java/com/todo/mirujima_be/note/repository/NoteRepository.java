package com.todo.mirujima_be.note.repository;

import com.todo.mirujima_be.note.entity.Note;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, CustomNoteRepository {

  @Modifying
  @Query("DELETE FROM Note n WHERE n.todo.id IN (SELECT t.id FROM Todo t WHERE t.goal.id = :goalId)")
  void deleteNotesByGoalId(@Param("goalId") Long goalId);

  @Query("""
          SELECT n FROM Note n
          LEFT JOIN FETCH n.todo t
          LEFT JOIN FETCH t.goal
          LEFT JOIN FETCH n.createdBy
          WHERE n.id IN :ids
      """)
  List<Note> findAllByIdIn(@Param("ids") Iterable<Long> ids);

  @Query("""
          SELECT n FROM Note n
          LEFT JOIN FETCH n.todo t
          LEFT JOIN FETCH t.goal
          LEFT JOIN FETCH n.createdBy
          WHERE n.id = :id
      """)
  Optional<Note> findById(@Param("id") long id);

}