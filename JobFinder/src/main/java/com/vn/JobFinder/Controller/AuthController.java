package com.vn.JobFinder.Controller;


import com.vn.JobFinder.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody String username,
            @RequestBody String password,
            @RequestBody String email,
            @RequestBody String phoneNumber) {
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
