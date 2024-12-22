package lk.hmpb.course.service.impl;

import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.repository.UserRepository;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
import lk.hmpb.course.util.ApiResponse;
import lk.hmpb.course.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Transformer transformer;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, Transformer transformer, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ApiResponse<UserResponseTo> register(UserRegisterReqTo userRegisterReqTo) {
        ApiResponse<UserResponseTo> response = new ApiResponse<>();

        if (existsByEmail(userRegisterReqTo.getEmail())) {
            response.setSuccess(false);
            response.setError("Email is already in use");
            return response;
        }

        userRegisterReqTo.setPassword(passwordEncoder.encode(userRegisterReqTo.getPassword()));
        User user = transformer.toUserEntity(userRegisterReqTo);
        User savedUser = userRepository.save(user);

        if (savedUser.getId() == null) {
            response.setSuccess(false);
            response.setError("Failed to save user: User ID not generated");
            return response;
        }

        String token = jwtUtil.generateToken(savedUser.getEmail(), String.valueOf(savedUser.getRole()));

        UserResponseTo userResponse = transformer.toUserResponseTO(savedUser);
        userResponse.set_id(savedUser.getId());
        userResponse.setToken(token);

        response.setSuccess(true);
        response.setData(userResponse);
        return response;
    }

    @Override
    public ApiResponse<UserResponseTo> login(UserLoginReqTo userLoginReqTo) {
        ApiResponse<UserResponseTo> response = new ApiResponse<>();

        Optional<User> userOptional = userRepository.findByEmail(userLoginReqTo.getEmail());

        if (userOptional.isEmpty()) {
            response.setSuccess(false);
            response.setError("User not found with email: " + userLoginReqTo.getEmail());
            return response;
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(userLoginReqTo.getPassword(), user.getPassword())) {
            response.setSuccess(false);
            response.setError("Invalid password");
            return response;
        }

        if (user.getId() == null) {
            response.setSuccess(false);
            response.setError("User ID is null, indicating a data integrity issue");
            return response;
        }

        String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));

        UserResponseTo userResponse = transformer.toUserResponseTO(user);
        userResponse.set_id(user.getId());
        userResponse.setToken(token);

        response.setSuccess(true);
        response.setData(userResponse);
        return response;
    }
}
