package com.todo.mirujima_be.note.repository;

import com.todo.mirujima_be.note.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, CustomNoteRepository {

}