package com.sparta.schedule.service;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.user.entity.User;
import com.sparta.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public ScheduleResponseDto createSchedule(Long userId, ScheduleRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        Schedule schedule = new Schedule(requestDto);
        user.addSchedule(schedule);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        return new ScheduleResponseDto(findSchedule(id));
    }

    public List<ScheduleResponseDto> getAllSchedules(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "updatedAt"));

        return scheduleRepository.findAll(pageable).stream().map(ScheduleResponseDto::new).toList();
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

    public Long addUserToSchedule(Long id, Long userId) {
        Schedule schedule = findSchedule(id);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );

        schedule.addUser(user);
        user.addSchedule(schedule);

        scheduleRepository.save(schedule);
        return id;
    }


    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Schedule not found"));
    }
}
