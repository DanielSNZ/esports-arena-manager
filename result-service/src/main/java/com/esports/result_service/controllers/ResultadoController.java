package com.esports.result_service.controllers;

import com.esports.result_service.models.Resultado;
import com.esports.result_service.models.dtos.ResultadoDTO;
import com.esports.result_service.services.ResultadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resultados")
@Validated
public class ResultadoController {

    @Autowired
    private ResultadoService resultadoService;

    @GetMapping
    public ResponseEntity<List<Resultado>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resultado> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.findById(id));
    }

    @GetMapping("/partida/{partidaId}")
    public ResponseEntity<List<Resultado>> findByPartidaId(@PathVariable Long partidaId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.findByPartidaId(partidaId));
    }

    @GetMapping("/ganador/{ganadorId}")
    public ResponseEntity<List<Resultado>> findByGanadorId(@PathVariable Long ganadorId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.findByGanadorId(ganadorId));
    }

    @GetMapping("/estado/{estadoValidacion}")
    public ResponseEntity<List<Resultado>> findByEstadoValidacion(@PathVariable String estadoValidacion) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.findByEstadoValidacion(estadoValidacion));
    }

    @PostMapping
    public ResponseEntity<Resultado> save(
            @Valid @RequestBody ResultadoDTO resultadoDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resultadoService.save(resultadoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Resultado> update(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoDTO resultadoDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resultadoService.updateById(id, resultadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        resultadoService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}