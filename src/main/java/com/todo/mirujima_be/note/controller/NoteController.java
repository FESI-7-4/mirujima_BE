package com.todo.mirujima_be.note.controller;

import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.note.dto.response.NoteDeletedResponse;
import com.todo.mirujima_be.note.dto.response.NoteDetailListResponse;
import com.todo.mirujima_be.note.dto.response.NoteDetailResponse;
import com.todo.mirujima_be.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mirujima/todo/note")
@Tag(name = "Note", description = "노트 관련 API")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "노트 모아보기 API", description = "노트 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteDetailListResponse> getNoteList(@ModelAttribute @Valid NoteListRequest noteListRequest) {
        var noteDetailList = noteService.getNoteList(noteListRequest);
        return new CommonResponse<NoteDetailListResponse>().success(noteDetailList);
    }

    @PostMapping
    @Operation(summary = "노트 등록 API", description = "노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> registerNote(@RequestBody @Valid NoteRegRequest noteRegRequest) {
        var noteId = noteService.registerNote(noteRegRequest);
        return new CommonResponse<Long>().success(noteId);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "노트 상세 조회 API", description = "노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteDetailResponse> getNoteDetail(@PathVariable(name = "noteId") long noteId) {
        var noteDetail = noteService.getNoteDetail(noteId);
        return new CommonResponse<NoteDetailResponse>().success(noteDetail);
    }

    @PutMapping
    @Operation(summary = "노트 수정 API", description = "노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<Long> modifyNote(@RequestBody @Valid NoteModRequest noteModRequest) {
        var taskNoteId = noteService.modifyNote(noteModRequest);
        return new CommonResponse<Long>().success(taskNoteId);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "노트 삭제 API", description = "노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteDeletedResponse> deleteNote(@PathVariable("noteId") long noteId) {
        var deletedNote = noteService.deleteNote(noteId);
        return new CommonResponse<NoteDeletedResponse>().success(deletedNote);
    }

}