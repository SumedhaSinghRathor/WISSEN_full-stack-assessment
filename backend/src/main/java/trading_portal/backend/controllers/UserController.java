package trading_portal.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import trading_portal.backend.entity.User;
import trading_portal.backend.repository.UserRepository;
import trading_portal.backend.services.UserService;

import java.util.List;

//@RestController
//@RequestMapping("/")
@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

//    @Autowired
//    private UserRepository userRepository;
//
//    // GET /users -> Get all users
//    @GetMapping("/users")
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//    // GET /users/{email} -> Get user by email (username)
//    @GetMapping("/users/{email}")
//    public User getUserByEmail(@PathVariable String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
//    }
//
//    // POST /users -> Add new user
//    @PostMapping("/users")
//    public User createUser(@RequestBody User user) {
//        return userRepository.save(user);
//    }

    @Autowired
    private UserService userService;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // LOGIN
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }
}