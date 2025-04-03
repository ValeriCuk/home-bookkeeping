package com.finance.home_bookkeeping.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String login;
    private String email;
    private String password;
    private boolean googleAuth;

    @OneToMany(mappedBy = "user")
    private Set<Operation> transactions;
}
