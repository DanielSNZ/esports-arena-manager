package com.esports.tournament_service.repositories;

import com.esports.tournament_service.models.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TorneoRepository extends JpaRepository<Torneo, Long> {

    List<Torneo> findByJuegoId(Long juegoId);

    List<Torneo> findByEstado(String estado);
}
