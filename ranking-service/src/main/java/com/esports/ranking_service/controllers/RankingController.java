package com.esports.ranking_service.controllers;

import com.esports.ranking_service.models.Ranking;
import com.esports.ranking_service.models.dtos.RankingDTO;
import com.esports.ranking_service.services.RankingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rankings")
@Validated
public class RankingController {

    @Autowired
    private RankingService rankingService;

    @GetMapping
    public ResponseEntity<List<Ranking>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.findById(id));
    }

    @GetMapping("/equipo/{equipoId}")
    public ResponseEntity<List<Ranking>> findByEquipoId(@PathVariable Long equipoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.findByEquipoId(equipoId));
    }

    @GetMapping("/torneo/{torneoId}")
    public ResponseEntity<List<Ranking>> findByTorneoId(@PathVariable Long torneoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.findByTorneoId(torneoId));
    }

    @GetMapping("/posicion/{posicion}")
    public ResponseEntity<List<Ranking>> findByPosicion(@PathVariable Integer posicion) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.findByPosicion(posicion));
    }

    @PostMapping
    public ResponseEntity<Ranking> save(
            @Valid @RequestBody RankingDTO rankingDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(rankingService.save(rankingDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ranking> update(
            @PathVariable Long id,
            @Valid @RequestBody RankingDTO rankingDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(rankingService.updateById(id, rankingDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        rankingService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}