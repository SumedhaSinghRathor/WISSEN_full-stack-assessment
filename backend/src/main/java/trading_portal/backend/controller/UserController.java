package trading_portal.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class UserController {

    // Simple GET endpoint
    @GetMapping("/hello")
    public String hello() {
        return "Backend is working!";
    }

    // Path variable test
    @GetMapping("/user/{name}")
    public String greetUser(@PathVariable String name) {
        return "Hello, " + name;
    }

    // Query param test
    @GetMapping("/age")
    public String getAge(@RequestParam int age) {
        return "Your age is " + age;
    }

    // POST test
    @PostMapping("/post")
    public String testPost(@RequestBody String data) {
        return "Received: " + data;
    }
}