package lk.hmpb.course.service;

import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.AllUserResponseTo;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    ApiResponse<UserResponseTo> register(UserRegisterReqTo userRegisterReqTo);
    ApiResponse<UserResponseTo>  login(UserLoginReqTo userLoginReqTo);
    ApiResponse<List<AllUserResponseTo>> getAllUsers(String token);
}
