package com.esports.notification_service.services;

import com.esports.notification_service.models.Notificacion;
import com.esports.notification_service.models.dtos.NotificacionDTO;

import java.util.List;

public interface NotificacionService {

    List<Notificacion> findAll();

    Notificacion findById(Long id);

    List<Notificacion> findByUsuarioId(Long usuarioId);

    List<Notificacion> findByTipo(String tipo);

    List<Notificacion> findByEstado(String estado);

    Notificacion save(NotificacionDTO notificacionDTO);

    Notificacion updateById(Long id, NotificacionDTO notificacionDTO);

    void deleteById(Long id);
}