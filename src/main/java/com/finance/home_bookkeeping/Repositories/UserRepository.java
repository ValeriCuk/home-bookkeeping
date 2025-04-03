package com.finance.home_bookkeeping.Repositories;

import com.finance.home_bookkeeping.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login); // Пошук за логіном
    Optional<User> findByEmail(String email);
}
