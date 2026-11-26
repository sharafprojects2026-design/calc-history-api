package com.age.calc.rest;

import com.age.calc.model.CalculHistorique;
import com.age.calc.repository.HistoriqueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/historique")
@Tag(name = "Historique", description = "Historique des calculs de l'utilisateur connecté")
@SecurityRequirement(name = "bearerAuth")
public class HistoriqueController {

    private final HistoriqueRepository repository;

    public HistoriqueController(HistoriqueRepository repository) {
        this.repository = repository;
    }

    public static class CalculRequest {
        @NotBlank
        public String expression;
        @NotBlank
        public String resultat;
    }

    @GetMapping
    @Operation(summary = "Lister l'historique de l'utilisateur")
    public List<CalculHistorique> lister(@RequestAttribute("utilisateur") String user) {
        return repository.findByUtilisateurOrderByDateCalculDesc(user);
    }

    @PostMapping
    @Operation(summary = "Enregistrer un calcul")
    public ResponseEntity<CalculHistorique> ajouter(
            @RequestAttribute("utilisateur") String user,
            @Valid @RequestBody CalculRequest req) {
        CalculHistorique c = new CalculHistorique();
        c.setUtilisateur(user);
        c.setExpression(req.expression);
        c.setResultat(req.resultat);
        CalculHistorique saved = repository.save(c);
        return ResponseEntity.created(URI.create("/historique/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un calcul")
    public ResponseEntity<Void> supprimer(
            @RequestAttribute("utilisateur") String user,
            @PathVariable Long id) {
        return repository.findByIdAndUtilisateur(id, user)
                .map(c -> {
                    repository.delete(c);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping
    @Transactional
    @Operation(summary = "Vider tout l'historique de l'utilisateur")
    public ResponseEntity<Void> toutSupprimer(@RequestAttribute("utilisateur") String user) {
        repository.deleteByUtilisateur(user);
        return ResponseEntity.noContent().build();
    }
}