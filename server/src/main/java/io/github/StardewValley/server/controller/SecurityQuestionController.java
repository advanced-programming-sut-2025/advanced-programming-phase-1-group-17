package io.github.StardewValley.server.controller;

import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.dto.RegisterRequest;
import io.github.StardewValley.shared.dto.RegisterResponse;
import io.github.StardewValley.shared.dto.SecurityQuestionRequest;
import io.github.StardewValley.shared.dto.SecurityQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class SecurityQuestionController {

    private final UserRepository userRepository;

    @Autowired
    public SecurityQuestionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("securityQuestion")
    public ResponseEntity<SecurityQuestionResponse> setSecurityQuestion(@RequestBody SecurityQuestionRequest req) {
        String username = req.getUsername();
        if (!userRepository.existsByUsername(username)) {
            return ResponseEntity.ok(new SecurityQuestionResponse(false, "Username not found"));
        }

        User user = userRepository.findByUsername(username).get();
        user.setSecurityQuestion(req.getQuestion());
        user.setSecurityAnswer(req.getAnswer());

        userRepository.save(user);
        return ResponseEntity.ok(new SecurityQuestionResponse(true, "Security Question and Answer set successfully."));
    }
}
