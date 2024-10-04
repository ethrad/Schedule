package com.sparta.schedule.dto;

import com.sparta.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String description;
    private LocalDate createAt;
    private LocalDate updateAt;

    public ScheduleResponseDto(Long id, String username, String description, LocalDate createAt, LocalDate updateAt) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUsername();
        this.description = schedule.getDescription();
        this.createAt = schedule.getCreateAt();
        this.updateAt = schedule.getUpdateAt();
    }
}
