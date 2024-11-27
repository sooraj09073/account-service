package com.sooraj.accountservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @ManyToOne
    @JoinColumn(name = "user_auth_id", nullable = false)
    private UserAuthentication userAuthentication;

}
