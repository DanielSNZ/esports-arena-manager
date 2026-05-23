package com.esports.team_service.repositories;

import com.esports.team_service.models.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByNombre(String nombre);

    List<Equipo> findByJuegoPrincipalId(Long juegoPrincipalId);

    List<Equipo> findByCapitanId(Long capitanId);

    List<Equipo> findByEstado(String estado);
}