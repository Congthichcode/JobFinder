package com.vn.JobFinder.Service;


import com.vn.JobFinder.Entity.UserEntity;
import com.vn.JobFinder.Repository.UserRepository;
import com.vn.JobFinder.Security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String register(String username, String password,String email,String phoneNumber) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Tài khoản đã tồn tại!");
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
        return "Đăng ký thành công!";
    }

    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Sai tên đăng nhập hoặc mật khẩu"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }
        return jwtTokenProvider.generateToken(username);
    }
}
