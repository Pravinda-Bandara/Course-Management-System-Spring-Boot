package lk.hmpb.course.service.impl;

import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.repository.UserRepository;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.*;
import lk.hmpb.course.util.ApiResponse;
import lk.hmpb.course.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Override
    public ApiResponse<List<AllUserResponseTo>> getAllUsers(String token) {
        ApiResponse<List<AllUserResponseTo>> response = new ApiResponse<>();

        try {
            String extToken = token.substring(7);
            String role = jwtUtil.extractRole(extToken);
            System.out.println(role);

            if (!"admin".equalsIgnoreCase(role)) {
                response.setSuccess(false);
                response.setError("Access denied: Only admin users can access this resource");
                return response;
            }

            List<User> users = userRepository.findAll();
            List<AllUserResponseTo> userResponses = users.stream()
                    .map(transformer::toAllUserResponseTO)
                    .toList();

            response.setSuccess(true);
            response.setData(userResponses);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setError("Error occurred while retrieving users: " + e.getMessage());
        }

        return response;
    }


    @Override
    public ApiResponse<AllUserResponseTo> getUserById(Long id) {
        ApiResponse<AllUserResponseTo> response = new ApiResponse<>();

        try {
            // Find the user by ID from the repository
            User user = userRepository.findById(id).orElse(null);

            if (user == null) {
                response.setSuccess(false);
                response.setError("User not found with ID: " + id);
                return response;
            }

            // Convert the User entity to a response DTO (UserResponse)
            AllUserResponseTo userResponse = transformer.toAllUserResponseTO(user);

            response.setSuccess(true);
            response.setData(userResponse);

        } catch (Exception e) {
            response.setSuccess(false);
            response.setError("Error occurred while retrieving the user: " + e.getMessage());
        }

        return response;
    }


    @Override
    public ApiResponse<UserResponseTo> updateUser(Long id, UserUpdateReqTo userUpdateReqTo) {
        ApiResponse<UserResponseTo> response = new ApiResponse<>();

        try {
            Optional<User> userOptional = userRepository.findById(id);

            if (userOptional.isEmpty()) {
                response.setSuccess(false);
                response.setError("User not found with ID: " + id);
                return response;
            }

            User user = userOptional.get();

            // Update user fields
            if (userUpdateReqTo.getName() != null) {
                user.setName(userUpdateReqTo.getName());
            }
            if (userUpdateReqTo.getEmail() != null && !user.getEmail().equals(userUpdateReqTo.getEmail())) {
                if (existsByEmail(userUpdateReqTo.getEmail())) {
                    response.setSuccess(false);
                    response.setError("Email is already in use");
                    return response;
                }
                user.setEmail(userUpdateReqTo.getEmail());
            }

            // Save updated user
            User updatedUser = userRepository.save(user);

            UserResponseTo userResponse = transformer.toUserResponseTO(updatedUser);
            userResponse.set_id(updatedUser.getId());

            response.setSuccess(true);
            response.setData(userResponse);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setError("Error occurred while updating user: " + e.getMessage());
        }

        return response;
    }
}
