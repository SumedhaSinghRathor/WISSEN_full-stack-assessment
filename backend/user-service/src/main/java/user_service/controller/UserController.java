package user_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import user_service.dto.ChangePasswordRequest;
import user_service.dto.LoginRequest;
import user_service.dto.LoginResponse;
import user_service.dto.UpdateWalletRequest;
import user_service.entity.User;
import user_service.repository.UserRepository;
import user_service.services.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepo;
    
    @GetMapping("/users")
    public List<User> getAllUsers() {
    	return userRepo.findAll();
    }
    

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
