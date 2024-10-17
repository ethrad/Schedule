package com.sparta.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    @NotEmpty
    @Size(min = 1, max = 500)
    private String content;
    @NotBlank
    private String username;
}
