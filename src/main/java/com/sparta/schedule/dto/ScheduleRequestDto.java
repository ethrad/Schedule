package com.sparta.schedule.dto;

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
    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;
    @NotNull
    @Size(min = 1, max = 500)
    private String description;
}
