package lk.hmpb.course.controller;

import jakarta.validation.Valid;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.to.AllUserResponseTo;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
import lk.hmpb.course.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserHttpController {

    private final UserService userService;

    public UserHttpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
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

    @PostMapping("/signin")
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
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<AllUserResponseTo>>> getAllUsers(@RequestHeader("Authorization") String token) {
        try {
            ApiResponse<List<AllUserResponseTo>> response = userService.getAllUsers(token);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
        } catch (Exception e) {
            ApiResponse<List<AllUserResponseTo>> errorResponse = new ApiResponse<>();
            errorResponse.setSuccess(false);
            errorResponse.setError("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
