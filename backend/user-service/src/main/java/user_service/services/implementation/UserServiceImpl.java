package user_service.services.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import user_service.entity.Roles;
import user_service.entity.User;
import user_service.repository.UserRepository;
import user_service.dto.ChangePasswordRequest;
import user_service.dto.LoginResponse;
import user_service.services.UserService;
import user_service.util.JwtUtil;

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
                user.getEmail(),
                user.getId(),
                user.getWallet());
	}

	@Override
	public String changePassword(ChangePasswordRequest request) {
		if (request == null || request.getUserId() <= 0 || request.getCurrentPassword() == null || request.getCurrentPassword().isBlank()) {
            throw new IllegalArgumentException("Invalid request data");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
	}

	@Override
	public String addWalletAmount(int userId, double amount) {
		if (userId <= 0) {
            throw new IllegalArgumentException("Invalid user id");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount to add must be greater than 0");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setWallet((user.getWallet() == null ? 0.0 : user.getWallet()) + amount);
        userRepository.save(user);
        return "Wallet updated successfully";
	}

	@Override
	public User getUserById(int id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	}

}
