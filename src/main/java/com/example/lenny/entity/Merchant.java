package com.example.lenny.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "merchants", schema = "lenny")
@Data
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
