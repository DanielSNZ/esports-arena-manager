package com.esports.result_service.services;

import com.esports.result_service.models.Resultado;
import com.esports.result_service.models.dtos.ResultadoDTO;

import java.util.List;

public interface ResultadoService {

    List<Resultado> findAll();

    Resultado findById(Long id);

    List<Resultado> findByPartidaId(Long partidaId);

    List<Resultado> findByGanadorId(Long ganadorId);

    List<Resultado> findByEstadoValidacion(String estadoValidacion);

    Resultado save(ResultadoDTO resultadoDTO);

    Resultado updateById(Long id, ResultadoDTO resultadoDTO);

    void deleteById(Long id);
}