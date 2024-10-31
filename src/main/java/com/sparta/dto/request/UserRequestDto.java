package com.sparta.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 30, message = "이름은 2글자 이상, 30글지 이하여야 합니다.")
    private String username;
    @Email(message = "이메일은 필수 입력 항목입니다.")
    private String email;
}
