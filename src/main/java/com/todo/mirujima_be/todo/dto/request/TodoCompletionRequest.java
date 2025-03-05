package com.todo.mirujima_be.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TodoCompletionRequest {

  @NotNull(message = "완료 여부는 필수 값입니다.")
  @Schema(description = "완료 여부", example = "true")
  private Boolean done;

}
