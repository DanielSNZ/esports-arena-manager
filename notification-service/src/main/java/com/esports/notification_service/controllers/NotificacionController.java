package com.esports.notification_service.controllers;

import com.esports.notification_service.models.Notificacion;
import com.esports.notification_service.models.dtos.NotificacionDTO;
import com.esports.notification_service.services.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Validated
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Notificacion>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.findByTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Notificacion>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Notificacion> save(
            @Valid @RequestBody NotificacionDTO notificacionDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificacionService.save(notificacionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> update(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionDTO notificacionDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notificacionService.updateById(id, notificacionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        notificacionService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}