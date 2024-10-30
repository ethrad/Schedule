package com.sparta.service;

import com.sparta.common.ApplicationException;
import com.sparta.common.ErrorCode;
import com.sparta.dto.ScheduleRequestDto;
import com.sparta.dto.ScheduleResponseDto;
import com.sparta.entity.Schedule;
import com.sparta.repository.ScheduleRepository;
import com.sparta.entity.User;
import com.sparta.repository.UserRepository;
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
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
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
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.DESC, "modifiedAt"));

        return scheduleRepository.findAll(pageable).stream().map(ScheduleResponseDto::new).toList();
    }

    public List<ScheduleResponseDto> getSchedulesByConditions(String username, LocalDateTime ldt) {
        return scheduleRepository.findByUsernameAndModifiedAt(username, ldt).stream().map(ScheduleResponseDto::new).toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto) {
        Schedule schedule = findSchedule(id);
        schedule.update(requestDto);
        return new ScheduleResponseDto(schedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = findSchedule(id);

        scheduleRepository.delete(schedule);
    }

    public ScheduleResponseDto addUserToSchedule(Long id, Long userId) {
        Schedule schedule = findSchedule(id);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );

        schedule.addUser(user);
        user.addSchedule(schedule);

        scheduleRepository.save(schedule);
        return new ScheduleResponseDto(schedule);
    }


    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new ApplicationException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
}
