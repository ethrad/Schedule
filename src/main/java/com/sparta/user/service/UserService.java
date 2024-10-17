package com.sparta.user.service;

import com.sparta.user.dto.UserRequestDto;
import com.sparta.user.dto.UserResponseDto;
import com.sparta.user.entity.User;
import com.sparta.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(requestDto);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    public UserResponseDto getUser(Long id) {
        return new UserResponseDto(findUser(id));
    }

    @Transactional
    public Long updateUser(Long id, UserRequestDto requestDto) {
        User user = findUser(id);
        user.update(requestDto);
        return id;
    }

    public Long deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
        return id;
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
    }
}
