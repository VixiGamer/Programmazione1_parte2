package com.example.provapratica.service;

import com.example.provapratica.entity.User;
import com.example.provapratica.entity.UserRepository;
import com.example.provapratica.entity.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class ProjectUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret:defaultSecretKey}")
    private String jwtSecret;

    @Autowired(required = false)
    private JwtUtils jwtUtils; // opzionale, se hai una classe JwtUtils

    // --- CREA NUOVO UTENTE ---
    public User addUser(User user) {
        user.setRoles(Collections.singletonList("USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    // --- RITORNA TUTTI GLI UTENTI ---
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    // --- TROVA UTENTE PER EMAIL ---
    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(repository.findByEmail(email));
    }

    // --- LOGIN UTENTE ---
    public AuthResponse login(User user) {
        User u = repository.findByEmail(user.getEmail());
        if (u != null && passwordEncoder.matches(user.getPassword(), u.getPassword())) {
            String accessToken = jwtUtils != null
                    ? jwtUtils.generateJwtToken(u.getEmail())
                    : "fake-access-token";
            String refreshToken = jwtUtils != null
                    ? jwtUtils.generateRefreshToken(u.getEmail())
                    : "fake-refresh-token";

            return new AuthResponse(accessToken, refreshToken);
        }
        return null;
    }

    // --- REFRESH TOKEN ---
    public AuthResponse reAuth(String refreshToken) throws Exception {
        AuthResponse authResponse = new AuthResponse();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            User user = repository.findByEmail(claims.getSubject());
            if (user != null) {
                String newAccess = jwtUtils != null
                        ? jwtUtils.generateJwtToken(user.getEmail())
                        : "fake-access-token";
                String newRefresh = jwtUtils != null
                        ? jwtUtils.generateRefreshToken(user.getEmail())
                        : "fake-refresh-token";

                authResponse.setAccessToken(newAccess);
                authResponse.setRefreshToken(newRefresh);
            }
        } catch (Exception e) {
            authResponse.setMsg("Errore durante la decodifica del token: " + e.getMessage());
            throw new Exception("Errore durante la decodifica del token", e);
        }
        return authResponse;
    }

    // --- ELIMINA UTENTE ---
    public String deleteUser(Integer userId) {
        repository.deleteById(userId);
        return "User with id " + userId + " has been deleted!";
    }
}
