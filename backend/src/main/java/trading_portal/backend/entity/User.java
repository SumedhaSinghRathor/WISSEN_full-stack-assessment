package trading_portal.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Roles role;
    private int age;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Portfolio> portfolios;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Roles getRole() { return role; }
    public void setRole(Roles role) { this.role = role; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public List<Portfolio> getPortfolios() {
        return portfolios;
    }
    public void setPortfolios(List<Portfolio> portfolios) {
        this.portfolios = portfolios;
    }
}