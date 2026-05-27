package com.esports.sanction_service.controllers;

import com.esports.sanction_service.models.Sancion;
import com.esports.sanction_service.models.dtos.SancionDTO;
import com.esports.sanction_service.services.SancionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sanciones")
@Validated
public class SancionController {

    @Autowired
    private SancionService sancionService;

    @GetMapping
    public ResponseEntity<List<Sancion>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sancion> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Sancion>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Sancion>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.findByTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Sancion>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Sancion> save(
            @Valid @RequestBody SancionDTO sancionDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sancionService.save(sancionDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sancion> update(
            @PathVariable Long id,
            @Valid @RequestBody SancionDTO sancionDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sancionService.updateById(id, sancionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        sancionService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}