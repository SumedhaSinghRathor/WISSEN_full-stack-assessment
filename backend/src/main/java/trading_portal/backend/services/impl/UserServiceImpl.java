package trading_portal.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import trading_portal.backend.dto.LoginResponse;
import trading_portal.backend.entity.Roles;
import trading_portal.backend.entity.User;
import trading_portal.backend.repository.UserRepository;
import trading_portal.backend.services.UserService;
import trading_portal.backend.util.JwtUtil;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String registerUser(User user) {
        if (user.getRole() == null) {
            user.setRole(Roles.USER);
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        if (user.getPassword() == null) {
            throw new RuntimeException("Password cannot be null");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    @Override
    public LoginResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user);
        return new LoginResponse(token,
                user.getRole() != null ? user.getRole().name() : Roles.USER.name(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
}
