package com.age.calc.rest;

import com.age.calc.model.Utilisateur;
import com.age.calc.repository.UtilisateurRepository;
import com.age.calc.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "Inscription et connexion")
public class AuthController {

    private final UtilisateurRepository repository;
    private final JwtUtil jwtUtil;

    public AuthController(UtilisateurRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public static class Identifiants {
        @NotBlank
        public String email;
        @NotBlank
        public String motDePasse;
    }

    @PostMapping("/register")
    @Operation(summary = "Créer un compte")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody Identifiants req) {
        if (repository.findByEmail(req.email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("erreur", "Email déjà utilisé"));
        }
        Utilisateur u = new Utilisateur();
        u.setEmail(req.email);
        u.setMotDePasse(BCrypt.hashpw(req.motDePasse, BCrypt.gensalt()));
        repository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Collections.singletonMap("token", jwtUtil.generer(u.getEmail())));
    }

    @PostMapping("/login")
    @Operation(summary = "Se connecter et obtenir un token")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody Identifiants req) {
        return repository.findByEmail(req.email)
                .filter(u -> BCrypt.checkpw(req.motDePasse, u.getMotDePasse()))
                .map(u -> ResponseEntity.ok(
                        Collections.singletonMap("token", jwtUtil.generer(u.getEmail()))))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("erreur", "Email ou mot de passe incorrect")));
    }
}