package com.example.demo.entity;


import com.example.demo.entity.data.UserRole;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String profileImageUrl;
    private String bio;

    // add UserRole
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    // Add enrollments
    // add courses
}
