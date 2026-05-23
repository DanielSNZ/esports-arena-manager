package com.esports.sanction_service.services;

import com.esports.sanction_service.exceptions.SancionException;
import com.esports.sanction_service.models.Sancion;
import com.esports.sanction_service.models.dtos.SancionDTO;
import com.esports.sanction_service.repositories.SancionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.esports.sanction_service.clients.UserClient;
import com.esports.sanction_service.models.dtos.UserResponseDTO;

import java.util.List;

@Service
public class SancionServiceImpl implements SancionService {

    @Autowired
    private SancionRepository sancionRepository;

    @Autowired
    private UserClient userClient;

    @Override
    public List<Sancion> findAll() {
        return this.sancionRepository.findAll();
    }

    @Override
    public Sancion findById(Long id) {
        return this.sancionRepository.findById(id).orElseThrow(
                () -> new SancionException("Sanción no encontrada")
        );
    }

    @Override
    public List<Sancion> findByUsuarioId(Long usuarioId) {
        return this.sancionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Sancion> findByTipo(String tipo) {
        return this.sancionRepository.findByTipo(tipo);
    }

    @Override
    public List<Sancion> findByEstado(String estado) {
        return this.sancionRepository.findByEstado(estado);
    }

    @Override
    public Sancion save(SancionDTO sancionDTO) {

        UserResponseDTO usuario = userClient.findById(sancionDTO.getUsuarioId());

        if (!usuario.getEstado().equals("ACTIVO")) {
            throw new SancionException("El usuario no está activo");
        }

        Sancion sancion = new Sancion();

        sancion.setUsuarioId(sancionDTO.getUsuarioId());
        sancion.setMotivo(sancionDTO.getMotivo());
        sancion.setTipo(sancionDTO.getTipo());
        sancion.setFechaInicio(sancionDTO.getFechaInicio());
        sancion.setFechaFin(sancionDTO.getFechaFin());
        sancion.setEstado(sancionDTO.getEstado());

        return this.sancionRepository.save(sancion);
    }

    @Override
    public Sancion updateById(Long id, SancionDTO sancionDTO) {

        return this.sancionRepository.findById(id).map(sancion -> {

            sancion.setUsuarioId(sancionDTO.getUsuarioId());
            sancion.setMotivo(sancionDTO.getMotivo());
            sancion.setTipo(sancionDTO.getTipo());
            sancion.setFechaInicio(sancionDTO.getFechaInicio());
            sancion.setFechaFin(sancionDTO.getFechaFin());
            sancion.setEstado(sancionDTO.getEstado());

            return this.sancionRepository.save(sancion);

        }).orElseThrow(
                () -> new SancionException("Sanción no encontrada")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.sancionRepository.existsById(id)) {
            throw new SancionException("Sanción no encontrada");
        }

        this.sancionRepository.deleteById(id);
    }
}