package com.esports.registration_service.services;

import com.esports.registration_service.exceptions.InscripcionException;
import com.esports.registration_service.models.Inscripcion;
import com.esports.registration_service.models.dtos.InscripcionDTO;
import com.esports.registration_service.repositories.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.registration_service.clients.TeamClient;
import com.esports.registration_service.clients.TournamentClient;
import com.esports.registration_service.models.dtos.TeamResponseDTO;
import com.esports.registration_service.models.dtos.TournamentResponseDTO;

import java.util.List;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private TournamentClient tournamentClient;

    @Autowired
    private TeamClient teamClient;

    @Override
    public List<Inscripcion> findAll() {
        return this.inscripcionRepository.findAll();
    }

    @Override
    public Inscripcion findById(Long id) {
        return this.inscripcionRepository.findById(id).orElseThrow(
                () -> new InscripcionException("Inscripción no encontrada")
        );
    }

    @Override
    public List<Inscripcion> findByTorneoId(Long torneoId) {
        return this.inscripcionRepository.findByTorneoId(torneoId);
    }

    @Override
    public List<Inscripcion> findByEquipoId(Long equipoId) {
        return this.inscripcionRepository.findByEquipoId(equipoId);
    }

    @Override
    public List<Inscripcion> findByEstado(String estado) {
        return this.inscripcionRepository.findByEstado(estado);
    }

    @Override
    public Inscripcion save(InscripcionDTO inscripcionDTO) {

        TournamentResponseDTO torneo = tournamentClient.findById(inscripcionDTO.getTorneoId());
        TeamResponseDTO equipo = teamClient.findById(inscripcionDTO.getEquipoId());

        if (!torneo.getEstado().equals("ACTIVO")) {
            throw new InscripcionException("El torneo no está activo");
        }

        if (!equipo.getEstado().equals("ACTIVO")) {
            throw new InscripcionException("El equipo no está activo");
        }

        if (inscripcionRepository.existsByTorneoIdAndEquipoId(
                inscripcionDTO.getTorneoId(),
                inscripcionDTO.getEquipoId())) {

            throw new InscripcionException(
                    "El equipo ya está inscrito en este torneo");
        }

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setTorneoId(inscripcionDTO.getTorneoId());
        inscripcion.setEquipoId(inscripcionDTO.getEquipoId());
        inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());
        inscripcion.setEstado(inscripcionDTO.getEstado());

        return this.inscripcionRepository.save(inscripcion);
    }

    @Override
    public Inscripcion updateById(Long id, InscripcionDTO inscripcionDTO) {

        return this.inscripcionRepository.findById(id).map(inscripcion -> {

            inscripcion.setTorneoId(inscripcionDTO.getTorneoId());
            inscripcion.setEquipoId(inscripcionDTO.getEquipoId());
            inscripcion.setFechaInscripcion(inscripcionDTO.getFechaInscripcion());
            inscripcion.setEstado(inscripcionDTO.getEstado());

            return this.inscripcionRepository.save(inscripcion);

        }).orElseThrow(
                () -> new InscripcionException("Inscripción no encontrada")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.inscripcionRepository.existsById(id)) {
            throw new InscripcionException("Inscripción no encontrada");
        }

        this.inscripcionRepository.deleteById(id);
    }
}