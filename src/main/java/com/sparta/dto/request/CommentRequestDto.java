package com.sparta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    @NotEmpty(message = "내용은 필수 입력 항목입니다.")
    @Size(min = 1, max = 500, message = "내용은 1글자 이상, 500글자 이하여야 합니다.")
    private String content;
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    private String username;
}
