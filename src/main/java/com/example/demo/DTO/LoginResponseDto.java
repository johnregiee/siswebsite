package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor // Creates a constructor with all arguments.
@NoArgsConstructor // Creates a default no-argument constructor.
public class LoginResponseDto {
    private Long id;
    private String email;
    private String role;
    // You could add a JWT token here in the future:
    // private String token; 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}