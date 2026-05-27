package com.esports.match_service.controllers;

import com.esports.match_service.models.Partida;
import com.esports.match_service.models.dtos.PartidaDTO;
import com.esports.match_service.services.PartidaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partidas")
@Validated
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @GetMapping
    public ResponseEntity<List<Partida>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.findById(id));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Partida>> findByTorneoId(@PathVariable Long torneoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.findByTorneoId(torneoId));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Partida>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.findByEstado(estado));
    }

    @GetMapping("/ganador/{ganadorId}")
    public ResponseEntity<List<Partida>> findByGanadorId(@PathVariable Long ganadorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.findByGanadorId(ganadorId));
    }

    @PostMapping
    public ResponseEntity<Partida> save(
            @Valid @RequestBody PartidaDTO partidaDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(partidaService.save(partidaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partida> update(
            @PathVariable Long id,
            @Valid @RequestBody PartidaDTO partidaDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(partidaService.updateById(id, partidaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        partidaService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}