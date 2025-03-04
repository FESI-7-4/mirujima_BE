package com.todo.mirujima_be.note.repository;

import com.todo.mirujima_be.note.entity.Note;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
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

  @EntityGraph(attributePaths = {"todo", "createdBy"})
  List<Note> findAllByIdIn(Iterable<Long> ids);

}