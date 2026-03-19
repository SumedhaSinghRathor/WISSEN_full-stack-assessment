package user_service.dto;

public class LoginResponse {
    private String token;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private int userId;
    private Double wallet;

    public LoginResponse(String token, String role, String firstName, String lastName, String email, int userId, Double wallet) {
        this.token = token;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userId = userId;
        this.wallet = wallet;
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
    public Double getWallet() { return wallet; }
    public void setWallet(Double wallet) { this.wallet = wallet; }
}
