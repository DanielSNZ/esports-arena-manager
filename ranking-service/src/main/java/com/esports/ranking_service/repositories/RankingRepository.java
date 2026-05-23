package com.esports.ranking_service.repositories;

import com.esports.ranking_service.models.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    List<Ranking> findByEquipoId(Long equipoId);

    List<Ranking> findByTorneoId(Long torneoId);

    List<Ranking> findByPosicion(Integer posicion);
}