package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Client - Request/Response - Controller

@Getter
@Setter
@RequiredArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();
        this.description = schedule.getDescription();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
