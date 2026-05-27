package com.esports.sanction_service.repositories;

import com.esports.sanction_service.models.Sancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SancionRepository extends JpaRepository<Sancion, Long> {

    List<Sancion> findByUsuarioId(Long usuarioId);

    List<Sancion> findByTipo(String tipo);

    List<Sancion> findByEstado(String estado);
}