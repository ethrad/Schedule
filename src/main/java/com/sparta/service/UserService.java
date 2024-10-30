package com.sparta.service;

import com.sparta.common.ApplicationException;
import com.sparta.common.ErrorCode;
import com.sparta.dto.request.UserRequestDto;
import com.sparta.dto.response.UserResponseDto;
import com.sparta.entity.User;
import com.sparta.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto requestDto) {
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new ApplicationException(ErrorCode.DUPLICATE_EMAIL);
        }

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
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = findUser(id);
        user.update(requestDto);
        return new UserResponseDto(user);
    }

    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ApplicationException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
