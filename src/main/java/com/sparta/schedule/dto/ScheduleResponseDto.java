package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String description;

    public ScheduleResponseDto(Long id, String username, String description) {
        this.id = id;
        this.username = username;
        this.description = description;
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.description = schedule.getDescription();
    }
}
