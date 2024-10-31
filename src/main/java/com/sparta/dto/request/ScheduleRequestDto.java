package com.sparta.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// Client - Request/Response - Controller

@Getter
@Setter
public class ScheduleRequestDto {
    @NotBlank
    private Long userId;
    @NotEmpty(message = "제목은 필수 입력 항목입니다.")
    @Size(min = 1, max = 100, message = "제목은 1글자 이상, 100글자 이하여야 합니다.")
    private String title;
    @NotNull(message = "내용은 필수 입력 항목입니다.")
    @Size(min = 1, max = 500, message = "내용은 1글자 이상, 500글자 이하여야 합니다.")
    private String description;
}
