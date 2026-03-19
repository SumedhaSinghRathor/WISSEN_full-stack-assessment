package trading_portal.backend.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // ✅ lowercase recommended
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    // ✅ FIXED (camelCase)
    private String firstName;
    private String lastName;

    // ✅ FIXED ENUM
    @Enumerated(EnumType.STRING)
    private Roles role;

    private int age;
    private Double wallet = 100000.0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    // getters & setters

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
    public Double getWallet() { return wallet; }
    public void setWallet(Double wallet) { this.wallet = wallet; }
}