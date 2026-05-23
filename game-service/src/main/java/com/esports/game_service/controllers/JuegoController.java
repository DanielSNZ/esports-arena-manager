package com.esports.game_service.controllers;

import com.esports.game_service.models.Juego;
import com.esports.game_service.services.JuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.esports.game_service.models.dtos.JuegoDTO;

import java.util.List;



@RestController
@RequestMapping("/api/v1/juegos")
@Validated
public class JuegoController {

    @Autowired
    private JuegoService juegoService;

    @GetMapping
    public ResponseEntity<List<Juego>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juegoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Juego> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juegoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Juego> save(
            @Valid @RequestBody JuegoDTO juegoDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(juegoService.save(juegoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Juego> update(
            @PathVariable Long id,
            @Valid @RequestBody JuegoDTO juegoDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(juegoService.updateById(id, juegoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        juegoService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}