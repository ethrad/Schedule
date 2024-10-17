package com.sparta.user.controller;

import com.sparta.user.dto.UserRequestDto;
import com.sparta.user.dto.UserResponseDto;
import com.sparta.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    UserService scheduleService;

    public UserController(UserService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return scheduleService.createUser(requestDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return scheduleService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable Long id) {
        return scheduleService.getUser(id);
    }

    @PutMapping("/{id}")
    public Long updateUser(@PathVariable Long id, @RequestBody UserRequestDto requestDto) {
        return scheduleService.updateUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public Long deleteUser(@PathVariable Long id) {
        return scheduleService.deleteUser(id);
    }
}
