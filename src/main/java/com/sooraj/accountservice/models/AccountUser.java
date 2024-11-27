package com.sooraj.accountservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class AccountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String emailId;
    private String phoneNumber;
    @OneToOne(mappedBy = "accountUser", cascade = CascadeType.MERGE, orphanRemoval = true)
    private UserAddress userAddress;
}
