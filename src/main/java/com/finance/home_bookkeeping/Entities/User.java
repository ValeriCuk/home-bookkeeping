package com.finance.home_bookkeeping.Entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

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

    public User() {
    }

    public User( String name, String surname, String login, String email, String password, boolean googleAuth, Set<Operation> transactions) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.password = password;
        this.googleAuth = googleAuth;
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGoogleAuth() {
        return googleAuth;
    }

    public void setGoogleAuth(boolean googleAuth) {
        this.googleAuth = googleAuth;
    }

    public Set<Operation> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Operation> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", googleAuth=" + googleAuth +
                ", transactions=" + transactions +
                '}';
    }
}
