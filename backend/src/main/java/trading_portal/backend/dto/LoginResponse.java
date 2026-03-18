package trading_portal.backend.dto;

public class LoginResponse {
    private String token;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private int userId;

    public LoginResponse() {}

    public LoginResponse(String token, String role, String firstName, String lastName, String email, int userId) {
        this.token = token;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
}
