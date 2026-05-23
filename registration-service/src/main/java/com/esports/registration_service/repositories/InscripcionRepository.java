package com.esports.registration_service.repositories;

import com.esports.registration_service.models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByTorneoId(Long torneoId);

    List<Inscripcion> findByEquipoId(Long equipoId);

    List<Inscripcion> findByEstado(String estado);

    boolean existsByTorneoIdAndEquipoId(Long torneoId, Long equipoId);
}