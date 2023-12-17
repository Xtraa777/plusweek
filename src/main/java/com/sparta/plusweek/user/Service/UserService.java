package com.sparta.plusweek.user.Service;

import com.sparta.plusweek.user.dto.UserRequestDto;
import com.sparta.plusweek.user.entity.User;
import com.sparta.plusweek.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();
        String checkPassword = userRequestDto.getCheckPassword();

        String encodedPassword = passwordEncoder.encode(password);
        String encodedCheckPassword = passwordEncoder.encode(checkPassword);

        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저 입니다.");
        }

        if (!passwordEncoder.matches(password, encodedCheckPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (password.contains(username)) {
            throw new IllegalArgumentException("비밀번호에 유저이름이 포함될 수 없습니다.");
        }

        User user = new User(username, encodedPassword);
        userRepository.save(user);
    }
}
