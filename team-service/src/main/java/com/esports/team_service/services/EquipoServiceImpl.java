package com.esports.team_service.services;

import com.esports.team_service.exceptions.EquipoException;
import com.esports.team_service.models.Equipo;
import com.esports.team_service.models.MiembroEquipo;
import com.esports.team_service.models.dtos.EquipoDTO;
import com.esports.team_service.models.dtos.MiembroEquipoDTO;
import com.esports.team_service.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.team_service.clients.UserClient;
import com.esports.team_service.models.dtos.UserResponseDTO;


import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public List<Equipo> findAll() {
        return this.equipoRepository.findAll();
    }

    @Override
    public Equipo findById(Long id) {
        return this.equipoRepository.findById(id).orElseThrow(
                () -> new EquipoException("Equipo no encontrado")
        );
    }

    @Override
    public List<Equipo> findByJuegoPrincipalId(Long juegoPrincipalId) {
        return this.equipoRepository.findByJuegoPrincipalId(juegoPrincipalId);
    }

    @Override
    public List<Equipo> findByCapitanId(Long capitanId) {
        return this.equipoRepository.findByCapitanId(capitanId);
    }

    @Override
    public List<Equipo> findByEstado(String estado) {
        return this.equipoRepository.findByEstado(estado);
    }

    @Override
    public Equipo save(EquipoDTO equipoDTO) {

        if (this.equipoRepository.findByNombre(equipoDTO.getNombre()).isPresent()) {
            throw new EquipoException("Equipo ya existe");
        }
        UserResponseDTO usuario = userClient.findById(equipoDTO.getCapitanId());

        if (!usuario.getEstado().equals("ACTIVO")) {
            throw new EquipoException("El capitán no está activo");
        }

        Equipo equipo = new Equipo();
        equipo.setNombre(equipoDTO.getNombre());
        equipo.setCapitanId(equipoDTO.getCapitanId());
        equipo.setJuegoPrincipalId(equipoDTO.getJuegoPrincipalId());
        equipo.setEstado(equipoDTO.getEstado());

        if (equipoDTO.getMiembros() != null) {
            for (MiembroEquipoDTO miembroDTO : equipoDTO.getMiembros()) {
                MiembroEquipo miembro = new MiembroEquipo();
                miembro.setUsuarioId(miembroDTO.getUsuarioId());
                miembro.setRolDentroEquipo(miembroDTO.getRolDentroEquipo());
                miembro.setEquipo(equipo);

                equipo.getMiembros().add(miembro);
            }
        }

        return this.equipoRepository.save(equipo);
    }

    @Override
    public Equipo updateById(Long id, EquipoDTO equipoDTO) {

        return this.equipoRepository.findById(id).map(equipo -> {

            equipo.setNombre(equipoDTO.getNombre());
            equipo.setCapitanId(equipoDTO.getCapitanId());
            equipo.setJuegoPrincipalId(equipoDTO.getJuegoPrincipalId());
            equipo.setEstado(equipoDTO.getEstado());

            equipo.getMiembros().clear();

            if (equipoDTO.getMiembros() != null) {
                for (MiembroEquipoDTO miembroDTO : equipoDTO.getMiembros()) {
                    MiembroEquipo miembro = new MiembroEquipo();
                    miembro.setUsuarioId(miembroDTO.getUsuarioId());
                    miembro.setRolDentroEquipo(miembroDTO.getRolDentroEquipo());
                    miembro.setEquipo(equipo);

                    equipo.getMiembros().add(miembro);
                }
            }

            return this.equipoRepository.save(equipo);

        }).orElseThrow(
                () -> new EquipoException("Equipo no encontrado")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.equipoRepository.existsById(id)) {
            throw new EquipoException("Equipo no encontrado");
        }

        this.equipoRepository.deleteById(id);
    }
}