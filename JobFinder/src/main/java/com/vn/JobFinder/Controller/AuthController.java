package com.vn.JobFinder.Controller;


import com.vn.JobFinder.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //Đăng ký tài khoản
    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String phoneNumber) {
        String message = authService.register(username, password, email, phoneNumber);
        return ResponseEntity.ok(message);
    }

    //  Đăng nhập lấy JWT Token
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        String token = authService.login(username, password);
        return ResponseEntity.ok(token);
    }
}
