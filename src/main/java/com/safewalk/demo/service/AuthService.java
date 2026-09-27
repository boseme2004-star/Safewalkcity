package com.safewalk.demo.service;

import com.safewalk.demo.dto.RegisterRequest;
import com.safewalk.demo.model.User;
import com.safewalk.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        // Check if phone number already exists
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone number is already registered");
        }

        // Create a new user
        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        // Encrypt the password before saving it
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // New users are ordinary users by default
        user.setRole(User.Role.USER);

        return userRepository.save(user);
    }

    public User login(String email, String password) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("Invalid email or password"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new RuntimeException("Invalid email or password");
    }

    return user;
}
}