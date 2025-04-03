package com.finance.home_bookkeeping.Controllers;

import com.finance.home_bookkeeping.CustomExceptions.UserAlreadyExistsException;
import com.finance.home_bookkeeping.CustomExceptions.WrongPasswordException;
import com.finance.home_bookkeeping.DTO.UserDTO;
import com.finance.home_bookkeeping.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("Реєстрація успішна!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
            userService.loginUser(userDTO);
            return ResponseEntity.ok("Вхід виконано!");
        }catch (UserAlreadyExistsException | WrongPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
