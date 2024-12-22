package lk.hmpb.course.controller;

import jakarta.validation.Valid;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserHttpController {

    private final UserService userService;

    public UserHttpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseTo>> register(@Valid @RequestBody UserRegisterReqTo userRegisterReqTo) {
        try {
            ApiResponse<UserResponseTo> response = userService.register(userRegisterReqTo);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            ApiResponse<UserResponseTo> errorResponse = new ApiResponse<>();
            errorResponse.setSuccess(false);
            errorResponse.setError("An error occurred during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseTo>> login(@Valid @RequestBody UserLoginReqTo userLoginReqTo) {
        try {
            ApiResponse<UserResponseTo> response = userService.login(userLoginReqTo);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            ApiResponse<UserResponseTo> errorResponse = new ApiResponse<>();
            errorResponse.setSuccess(false);
            errorResponse.setError("An error occurred during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
