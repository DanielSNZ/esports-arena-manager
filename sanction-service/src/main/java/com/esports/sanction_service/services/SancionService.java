package com.esports.sanction_service.services;

import com.esports.sanction_service.models.Sancion;
import com.esports.sanction_service.models.dtos.SancionDTO;

import java.util.List;

public interface SancionService {

    List<Sancion> findAll();

    Sancion findById(Long id);

    List<Sancion> findByUsuarioId(Long usuarioId);

    List<Sancion> findByTipo(String tipo);

    List<Sancion> findByEstado(String estado);

    Sancion save(SancionDTO sancionDTO);

    Sancion updateById(Long id, SancionDTO sancionDTO);

    void deleteById(Long id);
}