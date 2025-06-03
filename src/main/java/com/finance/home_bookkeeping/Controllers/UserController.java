package com.finance.home_bookkeeping.Controllers;

import com.finance.home_bookkeeping.CustomExceptions.UserAlreadyExistsException;
import com.finance.home_bookkeeping.DTO.UserDTO;
import com.finance.home_bookkeeping.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("Реєстрація почата! ->");
            userService.registerUser(userDTO);
            return ResponseEntity.ok("Реєстрація успішна!");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Внутрішня помилка сервера!");
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        System.out.println("GET /login");
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword());
            authenticationManager.authenticate(authToken);
            return ResponseEntity.ok("Вхід успішний!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Невірний логін або пароль!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Внутрішня помилка сервера!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Вихід успішний!");
    }
}
