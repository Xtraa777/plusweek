package com.sparta.plusweek.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$")
    private String username;
    @Pattern(regexp = "^{4,}$")
    private String password;
    private String checkPassword;
}
