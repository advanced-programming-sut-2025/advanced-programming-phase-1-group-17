package io.github.StardewValley.server.controller;

import io.github.StardewValley.shared.dto.AuthRequest;
import io.github.StardewValley.shared.dto.AuthResponse;
import io.github.StardewValley.server.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // Dummy check
        if ("admin".equals(request.username) && "1234".equals(request.password)) {
            String token = JwtUtil.generateToken(request.username);
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}
