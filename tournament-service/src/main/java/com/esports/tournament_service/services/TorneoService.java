package com.esports.tournament_service.services;

import com.esports.tournament_service.models.Torneo;
import com.esports.tournament_service.models.dtos.TorneoDTO;

import java.util.List;

public interface TorneoService {

    List<Torneo> findAll();

    Torneo findById(Long id);

    List<Torneo> findByJuegoId(Long juegoId);

    List<Torneo> findByEstado(String estado);

    Torneo save(TorneoDTO torneoDTO);

    Torneo updateById(Long id, TorneoDTO torneoDTO);

    void deleteById(Long id);
}