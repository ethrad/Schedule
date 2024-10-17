package com.sparta.schedule.dto;

import lombok.Getter;
import lombok.Setter;

// Client - Request/Response - Controller

@Getter
@Setter
public class ScheduleRequestDto {
    private Long userId;
    private String title;
    private String description;
}
