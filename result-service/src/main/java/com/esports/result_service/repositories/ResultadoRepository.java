package com.esports.result_service.repositories;

import com.esports.result_service.models.Resultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long> {

    List<Resultado> findByPartidaId(Long partidaId);

    List<Resultado> findByGanadorId(Long ganadorId);

    List<Resultado> findByEstadoValidacion(String estadoValidacion);
}