package lk.hmpb.course.service.impl;

import jakarta.transaction.Transactional;
import lk.hmpb.course.entiry.User;
import lk.hmpb.course.repository.UserRepository;
import lk.hmpb.course.service.UserService;
import lk.hmpb.course.service.util.Transformer;
import lk.hmpb.course.to.UserLoginReqTo;
import lk.hmpb.course.to.UserRegisterReqTo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Transformer transformer;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, Transformer transformer, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.transformer = transformer;
        this.passwordEncoder = passwordEncoder;
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
    public User save(UserRegisterReqTo userRegisterReqTo) {
        userRegisterReqTo.setPassword(passwordEncoder.encode(userRegisterReqTo.getPassword()));
        User user = transformer.fromUserTO(userRegisterReqTo);
        return userRepository.save(user);
    }

    @Override
    public User get(UserLoginReqTo userLoginReqTo) {
        // Check if user exists by email
        Optional<User> userOptional = userRepository.findByEmail(userLoginReqTo.getEmail());

        // If user does not exist, throw exception
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + userLoginReqTo.getEmail()); // User not found
        }

        // Check if the password matches
        User user = userOptional.get();
        if (!passwordEncoder.matches(userLoginReqTo.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password"); // Invalid password
        }

        // If everything is valid, return the user
        return user; // Successful login
    }
}
