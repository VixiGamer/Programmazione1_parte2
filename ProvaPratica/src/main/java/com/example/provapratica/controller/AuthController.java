package com.example.provapratica.controller;

import com.example.provapratica.dto.LoginRequest;
import com.example.provapratica.dto.LoginResponse;
import com.example.provapratica.entity.User;
import com.example.provapratica.security.JwtUtil;
import com.example.provapratica.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private ProjectUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //REGISTRAZIONE
    // POST http://localhost:8086/register
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
    }

    //LOGIN
    // POST http://localhost:8086/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userService.getUserByEmail(loginRequest.getEmail());

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenziali non valide");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRoles());
        return ResponseEntity.ok(new LoginResponse(token, user.getEmail(), user.getName()));
    }

    //DATI UTENTE AUTENTICATO
    // GET http://localhost:8086/me
    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    //RICARICA CREDITO PROPRIO
    // PATCH http://localhost:8086/me/credit/toup
    @PatchMapping("/me/credit/toup")
    public User rechargeMyCredit(Authentication authentication, @RequestParam Integer amount) {
        User user = (User) authentication.getPrincipal();
        if (amount <= 0) {
            throw new IllegalArgumentException("L'importo deve essere positivo");
        }
        return userService.rechargeCredit(user.getId(), amount);
    }

}
