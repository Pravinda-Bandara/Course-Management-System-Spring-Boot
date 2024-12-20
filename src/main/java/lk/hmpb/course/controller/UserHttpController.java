package lk.hmpb.course.controller;
import lk.hmpb.course.exception.AppException;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
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
    public ResponseEntity<UserResponseTo> register(@RequestBody UserRegisterReqTo userRegisterReqTo) {
        try {
            UserResponseTo response = userService.register(userRegisterReqTo);
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, "An error occurred during registration", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseTo> login(@RequestBody UserLoginReqTo userLoginReqTo) {
        try {
            UserResponseTo response = userService.login(userLoginReqTo);
            return ResponseEntity.ok(response);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException(500, "An error occurred during login", e);
        }
    }
}
