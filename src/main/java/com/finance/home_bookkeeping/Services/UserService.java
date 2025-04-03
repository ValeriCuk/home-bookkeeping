package com.finance.home_bookkeeping.Services;

import com.finance.home_bookkeeping.CustomExceptions.UserAlreadyExistsException;
import com.finance.home_bookkeeping.CustomExceptions.UserNotFoundException;
import com.finance.home_bookkeeping.CustomExceptions.WrongPasswordException;
import com.finance.home_bookkeeping.DTO.UserDTO;
import com.finance.home_bookkeeping.Entities.User;
import com.finance.home_bookkeeping.Repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Transactional
    public void registerUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if(isLoginTaken(userDTO.getLogin()))
            throw new UserAlreadyExistsException("Користувач з таким логіном вже існує!");
        if(isEmailTaken(userDTO.getEmail()))
            throw new UserAlreadyExistsException("Користувач з такою поштою вже існує!");
        userRepository.save(convertToEntity(userDTO));
    }

    public void loginUser(UserDTO userDTO) throws UserNotFoundException, WrongPasswordException {
        User user = userRepository.findByLogin(userDTO.getLogin())
                .orElseThrow(() -> new UserNotFoundException("Невірний логін!"));
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword()))
            throw new WrongPasswordException("Невірний пароль!");
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
