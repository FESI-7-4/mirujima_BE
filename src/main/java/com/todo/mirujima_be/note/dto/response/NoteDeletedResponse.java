package com.todo.mirujima_be.note.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDeletedResponse {

    @Schema(description = "노드 고유키", example = "1")
    private Long id;
    @Schema(description = "노트 제목", example = "노트 제목")
    private String title;
    @Schema(description = "노트 내용", example = "노트 내용")
    private String content;

}
