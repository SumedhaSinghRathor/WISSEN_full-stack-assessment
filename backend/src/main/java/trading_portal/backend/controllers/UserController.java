package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import trading_portal.backend.dto.ChangePasswordRequest;
import trading_portal.backend.dto.LoginRequest;
import trading_portal.backend.dto.LoginResponse;
import trading_portal.backend.dto.UpdateWalletRequest;
import trading_portal.backend.entity.User;
import trading_portal.backend.services.UserService;

//@RestController
//@RequestMapping("/")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // LOGIN
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PutMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

    @PutMapping("/wallet")
    public String updateWallet(@RequestBody UpdateWalletRequest request) {
        return userService.addWalletAmount(request.getUserId(), request.getAmount());
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public String handleRuntime(RuntimeException ex) {
        return ex.getMessage();
    }
}
