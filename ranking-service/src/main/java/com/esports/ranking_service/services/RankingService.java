package com.esports.ranking_service.services;

import com.esports.ranking_service.models.Ranking;
import com.esports.ranking_service.models.dtos.RankingDTO;

import java.util.List;

public interface RankingService {

    List<Ranking> findAll();

    Ranking findById(Long id);

    List<Ranking> findByEquipoId(Long equipoId);

    List<Ranking> findByTorneoId(Long torneoId);

    List<Ranking> findByPosicion(Integer posicion);

    Ranking save(RankingDTO rankingDTO);

    Ranking updateById(Long id, RankingDTO rankingDTO);

    void deleteById(Long id);
}