package com.example.provapratica.controller;

import com.example.provapratica.entity.User;
import com.example.provapratica.service.ProjectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private ProjectUserService userService;

    // 🔍 DETTAGLIO UTENTE PER ID (solo admin)
    // GET http://localhost:8086/users/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    // 📋 ELENCO DI TUTTI GLI UTENTI (solo admin)
    // GET http://localhost:8086/users/all
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ❌ ELIMINA UTENTE (solo admin)
    // DELETE http://localhost:8086/users/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }
}
