package lk.hmpb.course.service;

import lk.hmpb.course.entiry.User;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(UserRegisterReqTo userRegisterReqTo);
    User get(UserLoginReqTo userLoginReqTo);
}
