package com.esports.notification_service.services;

import com.esports.notification_service.exceptions.NotificacionException;
import com.esports.notification_service.models.Notificacion;
import com.esports.notification_service.models.dtos.NotificacionDTO;
import com.esports.notification_service.repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public List<Notificacion> findAll() {
        return this.notificacionRepository.findAll();
    }

    @Override
    public Notificacion findById(Long id) {
        return this.notificacionRepository.findById(id).orElseThrow(
                () -> new NotificacionException("Notificación no encontrada")
        );
    }

    @Override
    public List<Notificacion> findByUsuarioId(Long usuarioId) {
        return this.notificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Notificacion> findByTipo(String tipo) {
        return this.notificacionRepository.findByTipo(tipo);
    }

    @Override
    public List<Notificacion> findByEstado(String estado) {
        return this.notificacionRepository.findByEstado(estado);
    }

    @Override
    public Notificacion save(NotificacionDTO notificacionDTO) {

        Notificacion notificacion = new Notificacion();

        notificacion.setUsuarioId(notificacionDTO.getUsuarioId());
        notificacion.setMensaje(notificacionDTO.getMensaje());
        notificacion.setTipo(notificacionDTO.getTipo());
        notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
        notificacion.setEstado(notificacionDTO.getEstado());

        return this.notificacionRepository.save(notificacion);
    }

    @Override
    public Notificacion updateById(Long id, NotificacionDTO notificacionDTO) {

        return this.notificacionRepository.findById(id).map(notificacion -> {

            notificacion.setUsuarioId(notificacionDTO.getUsuarioId());
            notificacion.setMensaje(notificacionDTO.getMensaje());
            notificacion.setTipo(notificacionDTO.getTipo());
            notificacion.setFechaEnvio(notificacionDTO.getFechaEnvio());
            notificacion.setEstado(notificacionDTO.getEstado());

            return this.notificacionRepository.save(notificacion);

        }).orElseThrow(
                () -> new NotificacionException("Notificación no encontrada")
        );
    }

    @Override
    public void deleteById(Long id) {

        if (!this.notificacionRepository.existsById(id)) {
            throw new NotificacionException("Notificación no encontrada");
        }

        this.notificacionRepository.deleteById(id);
    }
}