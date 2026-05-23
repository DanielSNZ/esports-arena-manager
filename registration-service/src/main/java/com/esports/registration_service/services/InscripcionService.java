package com.esports.registration_service.services;

import com.esports.registration_service.models.Inscripcion;
import com.esports.registration_service.models.dtos.InscripcionDTO;

import java.util.List;

public interface InscripcionService {

    List<Inscripcion> findAll();

    Inscripcion findById(Long id);

    List<Inscripcion> findByTorneoId(Long torneoId);

    List<Inscripcion> findByEquipoId(Long equipoId);

    List<Inscripcion> findByEstado(String estado);

    Inscripcion save(InscripcionDTO inscripcionDTO);

    Inscripcion updateById(Long id, InscripcionDTO inscripcionDTO);

    void deleteById(Long id);
}