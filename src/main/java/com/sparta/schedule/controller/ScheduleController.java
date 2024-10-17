package com.sparta.schedule.controller;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.service.ScheduleService;
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

    @PostMapping
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    @GetMapping
    public List<ScheduleResponseDto> getAllSchedules(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNum,
                                                     @RequestParam(required = false, defaultValue = "10", value = "pageSize") int pageSize) {
        return scheduleService.getAllSchedules(pageNum, pageSize);
    }

    @GetMapping("/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @GetMapping("/byConditions")
    public List<ScheduleResponseDto> getSchedulesByConditions(@RequestParam(required = false) String username, @RequestParam(required = false) String ldt) {
        LocalDateTime localDateTime = (ldt != null && !ldt.isEmpty()) ? LocalDateTime.parse(ldt) : null;

        return scheduleService.getSchedulesByConditions(username, localDateTime);
    }

    @PutMapping("/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.updateSchedule(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteSchedule(@PathVariable Long id) {
        return scheduleService.deleteSchedule(id);
    }
}
