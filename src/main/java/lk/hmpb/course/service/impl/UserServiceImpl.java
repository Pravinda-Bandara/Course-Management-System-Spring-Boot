package lk.hmpb.course.service.impl;

import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.repository.UserRepository;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import lk.hmpb.course.to.UserResponseTo;
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
    public UserResponseTo register(UserRegisterReqTo userRegisterReqTo) {
        // Check if email already exists
        if (existsByEmail(userRegisterReqTo.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        // Encode password and save the user
        userRegisterReqTo.setPassword(passwordEncoder.encode(userRegisterReqTo.getPassword()));
        User user = transformer.toUserEntity(userRegisterReqTo);
        User savedUser = userRepository.save(user);

        if (savedUser.getId() == null) {
            throw new IllegalStateException("User ID was not generated during save operation");
        }
        System.out.println(savedUser.getId());

        // Generate token
        String token = jwtUtil.generateToken(savedUser.getEmail(), String.valueOf(savedUser.getRole()));

        // Transform to UserResponseTo
        UserResponseTo response = transformer.toUserResponseTO(savedUser);
        response.set_id(savedUser.getId());
        response.setToken(token);
        return response;
    }

    @Override
    public UserResponseTo login(UserLoginReqTo userLoginReqTo) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(userLoginReqTo.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + userLoginReqTo.getEmail());
        }

        User user = userOptional.get();

        // Validate password
        if (!passwordEncoder.matches(userLoginReqTo.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (user.getId() == null) {
            throw new IllegalStateException("User ID is null, which indicates a data integrity issue");
        }

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail(), String.valueOf(user.getRole()));

        // Transform to UserResponseTo
        UserResponseTo response = transformer.toUserResponseTO(user);
        response.set_id(user.getId());
        response.setToken(token);
        return response;
    }
}

