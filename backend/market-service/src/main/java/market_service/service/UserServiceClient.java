package market_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import market_service.dto.UserDTO;

@Service
public class UserServiceClient {
    @Autowired
    private RestTemplate restTemplate;

    public UserDTO getUserById(int userId) {
        try {
            String url = "http://USER-SERVICE/api/auth/user/" + userId;
            return restTemplate.getForObject(url, UserDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("User Service unavailable or user not found");
        }
    }
}
