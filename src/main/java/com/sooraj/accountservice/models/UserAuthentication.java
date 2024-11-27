package com.sooraj.accountservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "user_auth")
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    private String password;
    private boolean enabled;
    @OneToMany(mappedBy = "userAuthentication", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<UserRole> roles = new ArrayList<>();
}
