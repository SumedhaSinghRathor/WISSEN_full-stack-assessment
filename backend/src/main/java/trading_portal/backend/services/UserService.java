package trading_portal.backend.services;

import trading_portal.backend.dto.LoginResponse;
import trading_portal.backend.entity.User;

public interface UserService {
    String registerUser(User user);
    LoginResponse loginUser(String email, String password);
}
