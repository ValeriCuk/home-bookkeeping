package com.finance.home_bookkeeping.Services;

import com.finance.home_bookkeeping.CustomExceptions.UserAlreadyExistsException;
import com.finance.home_bookkeeping.CustomExceptions.UserNotFoundException;
import com.finance.home_bookkeeping.DTO.UserDTO;
import com.finance.home_bookkeeping.Entities.User;
import com.finance.home_bookkeeping.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService() {}

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if(isLoginTaken(userDTO.getLogin()))
            throw new UserAlreadyExistsException("Користувач з таким логіном вже існує!");
        if(isEmailTaken(userDTO.getEmail()))
            throw new UserAlreadyExistsException("Користувач з такою поштою вже існує!");
        userRepository.save(convertToEntity(userDTO));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException("Невірний логін!"));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                new ArrayList<>()
        );
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
