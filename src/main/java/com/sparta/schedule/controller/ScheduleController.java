package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ScheduleResponseDto> createSchedule(@PathVariable Long userId, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto responseDto = scheduleService.createSchedule(userId, requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNum,
                                                                     @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize) {
        List<ScheduleResponseDto> schedules = scheduleService.getAllSchedules(pageNum, pageSize);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
        ScheduleResponseDto schedule = scheduleService.getSchedule(id);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @GetMapping("/byConditions")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByConditions(@RequestParam(required = false) String username, @RequestParam(required = false) String ldt) {
        LocalDateTime localDateTime = (ldt != null && !ldt.isEmpty()) ? LocalDateTime.parse(ldt) : null;

        List<ScheduleResponseDto> schedules = scheduleService.getSchedulesByConditions(username, localDateTime);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        ScheduleResponseDto schedule = scheduleService.updateSchedule(id, requestDto);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/addUser")
    public ResponseEntity<ScheduleResponseDto> addUserToSchedule(@PathVariable Long id, @RequestParam Long userId) {
        ScheduleResponseDto schedule = scheduleService.addUserToSchedule(id, userId);
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }
}
