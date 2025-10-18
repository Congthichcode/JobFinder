package com.vn.JobFinder.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    // Token hết hạn sau 1 giờ (3600000 ms)
    private final long EXPIRATION = 1000 * 60 * 60;

    @PostConstruct
    public void init() {
        // Tạo key từ secret (bảo đảm không thay đổi giữa các lần khởi động)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Tạo token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Đặt username làm chủ thể (subject)
                .setIssuedAt(new Date()) // Ngày tạo token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // Hạn token
                .signWith(key, SignatureAlgorithm.HS256) // Ký token
                .compact(); // Tạo chuỗi JWT hoàn chỉnh
    }

    //  Lấy username từ token
    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //  Kiểm tra token hợp lệ hay không
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Token không hợp lệ: " + e.getMessage());
            return false;
        }
    }

    //  Check Git
}
