package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    ScheduleRepository scheduleRepository;
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public static List<ScheduleResponseDto> getSchedules() {
        return null;
    }

    public ScheduleResponseDto createMemo(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        return new ScheduleResponseDto(savedSchedule);
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        return null;
    }

    public Long deleteSchedule(Long id) {
        return null;
    }
}
