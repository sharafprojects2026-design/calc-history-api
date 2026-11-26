package com.age.calc.repository;

import com.age.calc.model.CalculHistorique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoriqueRepository extends JpaRepository<CalculHistorique, Long> {
    List<CalculHistorique> findByUtilisateurOrderByDateCalculDesc(String utilisateur);
    Optional<CalculHistorique> findByIdAndUtilisateur(Long id, String utilisateur);
    void deleteByUtilisateur(String utilisateur);
}