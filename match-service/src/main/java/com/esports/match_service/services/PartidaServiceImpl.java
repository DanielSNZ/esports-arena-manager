package com.esports.match_service.services;

import com.esports.match_service.exceptions.PartidaException;
import com.esports.match_service.models.Partida;
import com.esports.match_service.models.dtos.PartidaDTO;
import com.esports.match_service.repositories.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.match_service.clients.TeamClient;
import com.esports.match_service.models.dtos.TeamResponseDTO;

import java.util.List;

@Service
public class PartidaServiceImpl implements PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    @Autowired
    private TeamClient teamClient;

    @Override
    public List<Partida> findAll() {
        return this.partidaRepository.findAll();
    }

    @Override
    public Partida findById(Long id) {
        return this.partidaRepository.findById(id).orElseThrow(
                () -> new PartidaException("Partida no encontrada")
        );
    }

    @Override
    public List<Partida> findByTorneoId(Long torneoId) {
        return this.partidaRepository.findByTorneoId(torneoId);
    }

    @Override
    public List<Partida> findByEstado(String estado) {
        return this.partidaRepository.findByEstado(estado);
    }

    @Override
    public List<Partida> findByGanadorId(Long ganadorId) {
        return this.partidaRepository.findByGanadorId(ganadorId);
    }

    @Override
    public Partida save(PartidaDTO partidaDTO) {

        TeamResponseDTO equipoLocal = teamClient.findById(partidaDTO.getEquipoLocalId());
        TeamResponseDTO equipoVisitante = teamClient.findById(partidaDTO.getEquipoVisitanteId());

        if (!equipoLocal.getEstado().equals("ACTIVO")) {
            throw new PartidaException("El equipo local no está activo");
        }

        if (!equipoVisitante.getEstado().equals("ACTIVO")) {
            throw new PartidaException("El equipo visitante no está activo");
        }

        Partida partida = new Partida();

        partida.setTorneoId(partidaDTO.getTorneoId());
        partida.setEquipoLocalId(partidaDTO.getEquipoLocalId());
        partida.setEquipoVisitanteId(partidaDTO.getEquipoVisitanteId());
        partida.setFechaPartida(partidaDTO.getFechaPartida());
        partida.setEstado(partidaDTO.getEstado());
        partida.setGanadorId(partidaDTO.getGanadorId());

        return this.partidaRepository.save(partida);
    }

    @Override
    public Partida updateById(Long id, PartidaDTO partidaDTO) {

        return this.partidaRepository.findById(id).map(partida -> {

            partida.setTorneoId(partidaDTO.getTorneoId());
            partida.setEquipoLocalId(partidaDTO.getEquipoLocalId());
            partida.setEquipoVisitanteId(partidaDTO.getEquipoVisitanteId());
            partida.setFechaPartida(partidaDTO.getFechaPartida());
            partida.setEstado(partidaDTO.getEstado());
            partida.setGanadorId(partidaDTO.getGanadorId());

            return this.partidaRepository.save(partida);

        }).orElseThrow(
                () -> new PartidaException("Partida no encontrada")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.partidaRepository.existsById(id)) {
            throw new PartidaException("Partida no encontrada");
        }

        this.partidaRepository.deleteById(id);
    }
}