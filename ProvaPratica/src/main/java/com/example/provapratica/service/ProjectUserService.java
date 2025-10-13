package com.example.provapratica.service;

import com.example.provapratica.entity.User;
import com.example.provapratica.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class ProjectUserService {

    @Autowired
    private UserRepository repository;

    // REGISTRA UN NUOVO UTENTE
    public User addUser(User user) {
        if (user.getCredit() == null) {
            user.setCredit(BigDecimal.ZERO);
        }
        return repository.save(user);
    }

    // OTTIENI UTENTE PER EMAIL
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    // OTTIENI TUTTI GLI UTENTI
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    // OTTIENI UTENTE PER ID
    public Optional<User> getUserById(Integer id) {
        return repository.findById(id);
    }

    // RICARICA CREDITO
    public User rechargeCredit(Integer id, Integer amount) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setCredit(user.getCredit().add(BigDecimal.valueOf(amount)));
            return repository.save(user);
        } else {
            throw new IllegalArgumentException("Utente non trovato con ID: " + id);
        }
    }

    // ELIMINA UTENTE
    public String deleteUser(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Utente con ID " + id + " eliminato con successo.";
        } else {
            return "Nessun utente trovato con ID: " + id;
        }
    }
}
