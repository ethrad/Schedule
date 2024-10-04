package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        // Entity -> ResponseDto
        return new ScheduleResponseDto(savedSchedule);
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<ScheduleResponseDto> getSchedulesByConditions(String username, LocalDate date) {
        return scheduleRepository.findByConditions(username, date);
    }

    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            scheduleRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }

    public Long deleteSchedule(Long id, String password) {
        // 해당 메모가 DB에 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            if (Objects.equals(password.trim(), schedule.getPassword())) {
                scheduleRepository.delete(id);
                return id;
            } else {
                throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
            }
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id);
        if (schedule != null) {
            return new ScheduleResponseDto(schedule);
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }
}
