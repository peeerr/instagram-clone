package com.peeerr.instagram.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @Builder
    public User(String username, String password, String email, String name, String website, String bio, String phone, String gender, String profileImageUrl, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.website = website;
        this.bio = bio;
        this.phone = phone;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }

}
