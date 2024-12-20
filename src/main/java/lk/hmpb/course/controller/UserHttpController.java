package lk.hmpb.course.controller;

import lk.hmpb.course.entiry.User;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class UserHttpController {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    public UserHttpController(UserService userService, JwtUtil jwtUtil,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterReqTo userRegisterReqTo) {
        // Check if the email already exists
        if (userService.existsByEmail(userRegisterReqTo.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        // Save the user and encrypt the password inside the service
        User savedUser = userService.save(userRegisterReqTo);

        // Generate a JWT token for the registered user
        String token = jwtUtil.generateToken(savedUser.getEmail(), String.valueOf(savedUser.getRole()));

        // Return success message and token
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "token", token
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReqTo userLoginReqTo) {
        try {
            // Authenticate user and fetch the user details
            User user = userService.get(userLoginReqTo);

            // Generate a JWT token for the authenticated user
            String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));

            // Return success message and token
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "token", token
            ));
        } catch (IllegalArgumentException e) {
            // If authentication fails (e.g., invalid credentials)
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
