package io.github.StardewValley.server.controller.clientRequestHandlers;

import io.github.StardewValley.server.JwtService;
import io.github.StardewValley.shared.dto.*;
import io.github.StardewValley.server.model.User;
import io.github.StardewValley.server.repository.UserRepository;
import io.github.StardewValley.shared.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).get();
        UserDTO userDTO = new UserDTO(
            user.getUsername(),
            user.getNickName(),
            user.getGender(),
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getEmail(),
            user.getPasswordHash()
        );
        userDTO.setAvatar(user.getAvatar());
        userDTO.setNumOfPlay(user.getNumOfPlay());
        userDTO.setTheMostMoneyInGame(user.getTheMostMoneyInGame());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.ok(new RegisterResponse(false, "Username already taken"));
        }

        User user = new User(
            req.getUsername(),
            passwordEncoder.encode(req.getPassword()),
            req.getEmail(),
            req.getNickname(),
            req.getGender()
        );

        userRepository.save(user);
        return ResponseEntity.ok(new RegisterResponse(true, "User registered"));
    }

    @PostMapping("/passWordCheck")
    public ResponseEntity<Boolean> passWordCheck(@RequestHeader("Authorization") String authHeader,
                                                 @RequestBody String password) {
        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || !passwordEncoder.matches(password.substring(1, password.length() - 1), user.getPasswordHash())) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(
        @RequestHeader("Authorization") String authHeader,
        @RequestBody Map<String, String> passwordData) {

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        String newPassword = passwordData.get("newPassword");

        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return ResponseEntity.status(404).build();

        user.setPasswordHash(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return ResponseEntity.ok(jwtService.generateToken(user));
    }


    @PostMapping("/securityQuestion")
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByUsername(req.getUsername());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null, "User not found"));
        }

        User user = optionalUser.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, null, "Invalid credentials"));
        }

        String token = jwtService.generateToken(user);

        UserDTO userDTO = new UserDTO(
            user.getUsername(),
            user.getNickName(),
            user.getGender(),
            user.getSecurityQuestion(),
            user.getSecurityAnswer(),
            user.getEmail(),
            user.getPasswordHash()
        );
        return ResponseEntity.ok(new LoginResponse(token, userDTO, "Login successful"));
    }

    @PostMapping("/doesUserExist")
    public ResponseEntity<DoesUserExistResponse> doesUserExist(@RequestBody DoesUserExistRequest req) {
        boolean doesExist = userRepository.existsByUsername(req.getUsername());
        return ResponseEntity.ok(new DoesUserExistResponse(
            doesExist,
            doesExist ? "" : "No User found with username %s".formatted(req.getUsername())
        ));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@RequestBody ForgotPasswordRequest req) {
        User user = userRepository.findByUsername(req.getUsername()).get();
        String securityAnswer = req.getSecurityAnswer();
        if (!securityAnswer.equals(user.getSecurityAnswer())) {
            return ResponseEntity.ok(new ForgotPasswordResponse(false, "Security Answer is not correct."));
        }
        String password = generateStrongPassword(12);
        user.setPasswordHash(this.passwordEncoder.encode(password));
        userRepository.save(user);
        return ResponseEntity.ok(new ForgotPasswordResponse(true, password));
    }

    public String generateStrongPassword(int length) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String special = "!#$%^&*()=+{}[]|/:;'\"<>,?";

        String all = upper + lower + digits + special;
        SecureRandom rand = new SecureRandom();
        List<Character> password = new ArrayList<>();

        password.add(upper.charAt(rand.nextInt(upper.length())));
        password.add(lower.charAt(rand.nextInt(lower.length())));
        password.add(digits.charAt(rand.nextInt(digits.length())));
        password.add(special.charAt(rand.nextInt(special.length())));

        for (int i = 4; i < length; i++) {
            password.add(all.charAt(rand.nextInt(all.length())));
        }

        Collections.shuffle(password);

        StringBuilder result = new StringBuilder();
        for (char c : password) {
            result.append(c);
        }

        return result.toString();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllLobbies() {
        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(
        @RequestHeader("Authorization") String authHeader,
        @RequestParam String email,
        @RequestParam String avatar,
        @RequestParam String username,
        @RequestParam String nickname,
        @RequestParam double theMostMoneyInGame,
        @RequestParam String securityQuestion,
        @RequestParam String securityAnswer,
        @RequestParam int numOfPlay
    ) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        String realUsername = jwtService.extractUsername(token);
        User user = userRepository.findByUsername(realUsername).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();

        user.setEmail(email);
        user.setAvatar(avatar);
        user.setUsername(username);
        user.setNickName(nickname);
        user.setTheMostMoneyInGame(theMostMoneyInGame);
        user.setSecurityQuestion(securityQuestion);
        user.setSecurityAnswer(securityAnswer);
        user.setNumOfPlay(numOfPlay);

        userRepository.save(user);
        String string = jwtService.generateToken(user);

        return ResponseEntity.ok(string);
    }
    @PostMapping("/tokenIsValid")
    public ResponseEntity<Boolean> isTokenValid(@RequestParam String token) {
        if (!jwtService.isTokenExpired(token)) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }



}
