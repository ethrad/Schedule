package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String username;
    private String description;
    private int password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public void update(String username, String description, int password) {
        this.username = username;
        this.description = description;
        this.password = password;
        this.updateAt = updateAt; // 현재 시간 가져와서 설정
    }

    public Schedule(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.description = requestDto.getDescription();
        this.password = requestDto.getPassword();
    }
}
