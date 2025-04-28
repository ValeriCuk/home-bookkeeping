package com.finance.home_bookkeeping.Entities;

import com.finance.home_bookkeeping.Other.OperationType;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OperationType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Operation() {
    }

    public Long getId() {
        return id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
