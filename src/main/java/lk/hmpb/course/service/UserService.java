package lk.hmpb.course.service;

import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.ReqTO.UserLoginReqTo;
import lk.hmpb.course.to.ReqTO.UserRegisterReqTo;
import lk.hmpb.course.to.ReqTO.UserUpdateReqTo;
import lk.hmpb.course.to.ResTO.AllUserResponseTo;
import lk.hmpb.course.to.ResTO.UserResponseTo;
import lk.hmpb.course.util.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    ApiResponse<UserResponseTo> register(UserRegisterReqTo userRegisterReqTo);
    ApiResponse<UserResponseTo>  login(UserLoginReqTo userLoginReqTo);
    ApiResponse<List<AllUserResponseTo>> getAllUsers(String token);
    public ApiResponse<AllUserResponseTo> getUserById(Long id);
    ApiResponse<UserResponseTo> updateUser(Long id, UserUpdateReqTo userUpdateReqTo);
    ApiResponse<String> deleteUser(Long id);
}
