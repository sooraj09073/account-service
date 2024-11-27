package com.sooraj.accountservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    @OneToOne
    @JoinColumn(name = "account_user_id")
    private AccountUser accountUser;
}
