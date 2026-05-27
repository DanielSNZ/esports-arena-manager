package com.esports.tournament_service.controllers;

import com.esports.tournament_service.models.Torneo;
import com.esports.tournament_service.models.dtos.TorneoDTO;
import com.esports.tournament_service.services.TorneoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/torneos")
@Validated
public class TorneoController {

    @Autowired
    private TorneoService torneoService;

    @GetMapping
    public ResponseEntity<List<Torneo>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(torneoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Torneo> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(torneoService.findById(id));
    }

    @GetMapping("/juego/{juegoId}")
    public ResponseEntity<List<Torneo>> findByJuegoId(@PathVariable Long juegoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(torneoService.findByJuegoId(juegoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Torneo>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(torneoService.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<Torneo> save(
            @Valid @RequestBody TorneoDTO torneoDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(torneoService.save(torneoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Torneo> update(
            @PathVariable Long id,
            @Valid @RequestBody TorneoDTO torneoDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(torneoService.updateById(id, torneoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        torneoService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}