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
        User user = transformer.toUserEntity(userRegisterReqTo);
        User savedUser = userRepository.save(user);

        if (savedUser.getId() == null) {
            throw new IllegalStateException("User ID was not generated during save operation");
        }
        return savedUser;
    }

    @Override
    public User get(UserLoginReqTo userLoginReqTo) {
        Optional<User> userOptional = userRepository.findByEmail(userLoginReqTo.getEmail());

        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found with email: " + userLoginReqTo.getEmail()); // User not found
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(userLoginReqTo.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        if (user.getId() == null) {
            throw new IllegalStateException("User ID is null, which indicates a data integrity issue");
        }
        return user;
    }
}
