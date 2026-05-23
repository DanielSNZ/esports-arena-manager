package com.esports.registration_service.controllers;

import com.esports.registration_service.models.Inscripcion;
import com.esports.registration_service.models.dtos.InscripcionDTO;
import com.esports.registration_service.services.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inscripciones")
@Validated
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<List<Inscripcion>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.findById(id));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Inscripcion>> findByTorneoId(@PathVariable Long torneoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.findByTorneoId(torneoId));
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<Inscripcion>> findByEquipoId(@PathVariable Long equipoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.findByEquipoId(equipoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Inscripcion>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Inscripcion> save(
            @Valid @RequestBody InscripcionDTO inscripcionDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inscripcionService.save(inscripcionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> update(
            @PathVariable Long id,
            @Valid @RequestBody InscripcionDTO inscripcionDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(inscripcionService.updateById(id, inscripcionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        inscripcionService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}