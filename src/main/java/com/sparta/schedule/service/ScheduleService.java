package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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

    public ScheduleResponseDto getSchedule(Long id) {
        return new ScheduleResponseDto(scheduleRepository.findById(id).orElseThrow());
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByUpdatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public List<ScheduleResponseDto> getSchedulesByConditions(String username, LocalDateTime ldt) {
        return scheduleRepository.findByUsernameAndUpdatedAt(username, ldt).stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public Long updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = findSchedule(id);
        schedule.update(requestDto);
        return id;
    }

    public Long deleteSchedule(Long id) {
        Schedule schedule = findSchedule(id);

        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id){
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Schedule not found"));
    }


}
