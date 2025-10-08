package com.example.provapratica.controller;

import com.example.provapratica.entity.User;
import com.example.provapratica.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProjectUserService userService;

    // 🟩 REGISTRAZIONE UTENTE
    // POST http://localhost:8086/user/register
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    // 🔍 DETTAGLIO UTENTE PER ID
    // GET http://localhost:8086/user/{id}
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // 💰 RICARICA CREDITO
    // PUT http://localhost:8086/user/{id}/recharge?amount=50
    @PutMapping("/{id}/recharge")
    public User rechargeCredit(@PathVariable Integer id, @RequestParam Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("L'importo deve essere positivo");
        }
        return userService.rechargeCredit(id, amount);
    }

    // 📋 ELENCO DI TUTTI GLI UTENTI (facoltativo)
    // GET http://localhost:8086/user/all
    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
