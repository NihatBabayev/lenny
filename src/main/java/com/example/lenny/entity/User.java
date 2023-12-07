package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users",schema = "lenny")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String password;
    private String role;
    @Column(name = "is_active")
    private boolean isActive;
    @OneToOne(mappedBy = "user")
    private Merchant merchant;
    @OneToOne(mappedBy = "user")
    private Customer customer;
}