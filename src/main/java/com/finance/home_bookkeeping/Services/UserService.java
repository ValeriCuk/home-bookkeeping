package com.finance.home_bookkeeping.Services;

import com.finance.home_bookkeeping.DTO.UserDTO;
import com.finance.home_bookkeeping.Entities.User;
import com.finance.home_bookkeeping.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String registerUser(UserDTO userDTO) {
        if(isLoginTaken(userDTO.getLogin()))
            return "Користувач з таким логіном вже існує!";

        if(isEmailTaken(userDTO.getEmail()))
            return "Користувач з такою поштою вже існує!";

        userRepository.save(convertToEntity(userDTO));
        return "Реєстрація успішна!";
    }

    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);
        return user;
    }

    private boolean isLoginTaken(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
