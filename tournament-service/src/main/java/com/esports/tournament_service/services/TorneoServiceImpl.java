package com.esports.tournament_service.services;

import com.esports.tournament_service.exceptions.TorneoException;
import com.esports.tournament_service.models.Torneo;
import com.esports.tournament_service.models.dtos.TorneoDTO;
import com.esports.tournament_service.repositories.TorneoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.tournament_service.clients.GameClient;
import com.esports.tournament_service.models.dtos.GameResponseDTO;

import java.util.List;

@Service
public class TorneoServiceImpl implements TorneoService {


    @Autowired
    private TorneoRepository torneoRepository;

    @Autowired
    private GameClient gameClient;

    @Override
    public List<Torneo> findAll() {
        return this.torneoRepository.findAll();
    }

    @Override
    public Torneo findById(Long id) {
        return this.torneoRepository.findById(id).orElseThrow(
                () -> new TorneoException("Torneo no encontrado")
        );
    }

    @Override
    public List<Torneo> findByJuegoId(Long juegoId) {
        return this.torneoRepository.findByJuegoId(juegoId);
    }

    @Override
    public List<Torneo> findByEstado(String estado) {
        return this.torneoRepository.findByEstado(estado);
    }

    @Override
    public Torneo save(TorneoDTO torneoDTO) {
        GameResponseDTO juego = gameClient.findById(torneoDTO.getJuegoId());

        if (!juego.getEstado().equals("ACTIVO")) {
            throw new TorneoException("El juego no está activo");
        }

        Torneo torneo = new Torneo();

        torneo.setNombre(torneoDTO.getNombre());
        torneo.setJuegoId(torneoDTO.getJuegoId());
        torneo.setFechaInicio(torneoDTO.getFechaInicio());
        torneo.setFechaFin(torneoDTO.getFechaFin());
        torneo.setCupoMaximo(torneoDTO.getCupoMaximo());
        torneo.setEstado(torneoDTO.getEstado());
        torneo.setModalidad(torneoDTO.getModalidad());

        return this.torneoRepository.save(torneo);
    }

    @Override
    public Torneo updateById(Long id, TorneoDTO torneoDTO) {

        return this.torneoRepository.findById(id).map(torneo -> {

            torneo.setNombre(torneoDTO.getNombre());
            torneo.setJuegoId(torneoDTO.getJuegoId());
            torneo.setFechaInicio(torneoDTO.getFechaInicio());
            torneo.setFechaFin(torneoDTO.getFechaFin());
            torneo.setCupoMaximo(torneoDTO.getCupoMaximo());
            torneo.setEstado(torneoDTO.getEstado());
            torneo.setModalidad(torneoDTO.getModalidad());

            return this.torneoRepository.save(torneo);

        }).orElseThrow(
                () -> new TorneoException("Torneo no encontrado")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.torneoRepository.existsById(id)) {
            throw new TorneoException("Torneo no encontrado");
        }

        this.torneoRepository.deleteById(id);
    }
}