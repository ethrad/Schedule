package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String username;
    private String description;
    private String password;
    private LocalDate createAt;
    private LocalDate updateAt;

    public void update(String username, String description, String password) {
        this.username = username;
        this.description = description;
        this.password = password;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
    }

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.description = requestDto.getDescription();
        this.password = requestDto.getPassword();
        this.updateAt = LocalDate.now();
    }
}
