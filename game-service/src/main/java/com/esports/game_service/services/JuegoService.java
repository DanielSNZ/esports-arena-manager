package com.esports.game_service.services;

import com.esports.game_service.models.Juego;
import com.esports.game_service.models.dtos.JuegoDTO;

import java.util.List;

public interface JuegoService {

    List<Juego> findAll();

    Juego findById(Long id);

    Juego save(JuegoDTO juegoDTO);

    void deleteById(Long id);

    Juego updateById(Long id, JuegoDTO juegoDTO);
}