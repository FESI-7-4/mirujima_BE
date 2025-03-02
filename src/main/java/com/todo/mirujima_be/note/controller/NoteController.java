package com.todo.mirujima_be.note.controller;

import com.todo.mirujima_be.auth.util.AuthUtil;
import com.todo.mirujima_be.common.dto.CommonResponse;
import com.todo.mirujima_be.note.dto.NotePageCollection;
import com.todo.mirujima_be.note.dto.request.NoteListRequest;
import com.todo.mirujima_be.note.dto.request.NoteModRequest;
import com.todo.mirujima_be.note.dto.request.NoteRegRequest;
import com.todo.mirujima_be.note.dto.response.NoteResponse;
import com.todo.mirujima_be.note.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mirujima/notes")
@Tag(name = "Note", description = "노트 관련 API")
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "노트 목록 조회 API", description = "노트 목록을 조회합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NotePageCollection> getNoteList(@ModelAttribute @Valid NoteListRequest noteListRequest) {
        var response = noteService.getNoteList(AuthUtil.getEmail(), noteListRequest);
        return new CommonResponse<NotePageCollection>().success(response);
    }

    @PostMapping
    @Operation(summary = "노트 등록 API", description = "노트를 등록합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteResponse> registerNote(@RequestBody @Valid NoteRegRequest noteRegRequest) {
        var response = noteService.createNote(AuthUtil.getEmail(), noteRegRequest);
        return new CommonResponse<NoteResponse>().success(response);
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "노트 상세 조회 API", description = "노트 상세 조회를 합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteResponse> getNoteDetail(@PathVariable(name = "noteId") long id) {
        var response = noteService.getNoteDetail(AuthUtil.getEmail(), id);
        return new CommonResponse<NoteResponse>().success(response);
    }

    @PatchMapping("/{noteId}")
    @Operation(summary = "노트 수정 API", description = "노트를 수정합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<NoteResponse> modifyNote(
            @PathVariable(name = "noteId") long id, @RequestBody @Valid NoteModRequest noteModRequest
    ) {
        var response = noteService.modifyNote(AuthUtil.getEmail(), id, noteModRequest);
        return new CommonResponse<NoteResponse>().success(response);
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "노트 삭제 API", description = "노트를 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    public CommonResponse<?> deleteNote(@PathVariable("noteId") long noteId) {
        noteService.deleteNote(AuthUtil.getEmail(), noteId);
        return new CommonResponse<>().success(null);
    }

}