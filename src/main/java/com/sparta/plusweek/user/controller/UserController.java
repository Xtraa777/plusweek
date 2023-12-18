package com.sparta.plusweek.user.controller;

import com.sparta.plusweek.jwt.JwtUtil;
import com.sparta.plusweek.user.Service.UserService;
import com.sparta.plusweek.user.dto.CommonResponseDto;
import com.sparta.plusweek.user.dto.UserRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        try {
            userService.signup(userRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        try {
            userService.login(userRequestDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(new CommonResponseDto(exception.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }

        jwtUtil.addJwtToCookie(jwtUtil.createToken(userRequestDto.getUsername()), response);

        return ResponseEntity.ok()
                .body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
    }
}