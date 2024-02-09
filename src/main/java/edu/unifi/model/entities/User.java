package edu.unifi.model.entities;

import edu.unifi.model.util.security.Roles;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@jakarta.persistence.Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private Roles role;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "username", unique = true, length = 50)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) { this.role = role; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
