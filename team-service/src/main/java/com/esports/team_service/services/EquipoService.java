package com.esports.team_service.services;

import com.esports.team_service.models.Equipo;
import com.esports.team_service.models.dtos.EquipoDTO;

import java.util.List;

public interface EquipoService {

    List<Equipo> findAll();

    Equipo findById(Long id);

    List<Equipo> findByJuegoPrincipalId(Long juegoPrincipalId);

    List<Equipo> findByCapitanId(Long capitanId);

    List<Equipo> findByEstado(String estado);

    Equipo save(EquipoDTO equipoDTO);

    Equipo updateById(Long id, EquipoDTO equipoDTO);

    void deleteById(Long id);
}