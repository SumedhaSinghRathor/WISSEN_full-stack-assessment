package user_service.services;

import user_service.dto.ChangePasswordRequest;
import user_service.dto.LoginResponse;
import user_service.entity.User;

public interface UserService {
    String registerUser(User user);
    LoginResponse loginUser(String email, String password);
    String changePassword(ChangePasswordRequest request);
    String addWalletAmount(int userId, double amount);
    User getUserById(int id);
}
