package com.esports.team_service.controllers;

import com.esports.team_service.models.Equipo;
import com.esports.team_service.models.dtos.EquipoDTO;
import com.esports.team_service.services.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
@Validated
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    public ResponseEntity<List<Equipo>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipo> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.findById(id));
    }

    @GetMapping("/juego/{juegoPrincipalId}")
    public ResponseEntity<List<Equipo>> findByJuegoPrincipalId(@PathVariable Long juegoPrincipalId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.findByJuegoPrincipalId(juegoPrincipalId));
    }

    @GetMapping("/capitan/{capitanId}")
    public ResponseEntity<List<Equipo>> findByCapitanId(@PathVariable Long capitanId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.findByCapitanId(capitanId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Equipo>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Equipo> save(@Valid @RequestBody EquipoDTO equipoDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(equipoService.save(equipoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipo> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipoDTO equipoDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(equipoService.updateById(id, equipoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipoService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}