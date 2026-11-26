package com.age.calc.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique")
public class CalculHistorique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String utilisateur;

    @Column(nullable = false)
    private String expression;

    @Column(nullable = false)
    private String resultat;

    private LocalDateTime dateCalcul;

    @PrePersist
    void avantSauvegarde() {
        if (dateCalcul == null) {
            dateCalcul = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUtilisateur() { return utilisateur; }
    public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }
    public String getExpression() { return expression; }
    public void setExpression(String expression) { this.expression = expression; }
    public String getResultat() { return resultat; }
    public void setResultat(String resultat) { this.resultat = resultat; }
    public LocalDateTime getDateCalcul() { return dateCalcul; }
    public void setDateCalcul(LocalDateTime dateCalcul) { this.dateCalcul = dateCalcul; }
}