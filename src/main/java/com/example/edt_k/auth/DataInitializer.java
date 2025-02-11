package com.example.edt_k.auth;


import com.example.edt_k.entity.Utilisateur;
import com.example.edt_k.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (utilisateurRepository.count() == 0) {
            // Créer un utilisateur par défaut
            Utilisateur user = new Utilisateur();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            utilisateurRepository.save(user);

        }
    }
}
