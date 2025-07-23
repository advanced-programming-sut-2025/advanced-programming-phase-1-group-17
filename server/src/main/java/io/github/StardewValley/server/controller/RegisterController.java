package io.github.StardewValley.server.controller;

import io.github.StardewValley.shared.dto.RegisterRequest;
import io.github.StardewValley.shared.dto.RegisterResponse;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.ok(new RegisterResponse(false, "Username already taken"));
        }

        User user = new User(
            req.getUsername(),
            passwordEncoder.encode(req.getPassword()),
            req.getNickname(),
            req.getEmail(),
            req.getGender()
        );

        userRepository.save(user);
        return ResponseEntity.ok(new RegisterResponse(true, "User registered"));
    }
}
