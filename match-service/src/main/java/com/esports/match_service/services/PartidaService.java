package com.esports.match_service.services;

import com.esports.match_service.models.Partida;
import com.esports.match_service.models.dtos.PartidaDTO;

import java.util.List;

public interface PartidaService {

    List<Partida> findAll();

    Partida findById(Long id);

    List<Partida> findByTorneoId(Long torneoId);

    List<Partida> findByEstado(String estado);

    List<Partida> findByGanadorId(Long ganadorId);

    Partida save(PartidaDTO partidaDTO);

    Partida updateById(Long id, PartidaDTO partidaDTO);

    void deleteById(Long id);
}
