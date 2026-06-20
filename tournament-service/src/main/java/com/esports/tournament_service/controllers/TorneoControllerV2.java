package com.esports.tournament_service.controllers;

import com.esports.tournament_service.assemblers.TorneoModelAssembler;
import com.esports.tournament_service.models.Torneo;
import com.esports.tournament_service.models.dtos.TorneoDTO;
import com.esports.tournament_service.services.TorneoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/torneos")
@Validated
@Tag(name = "Torneos V2", description = "Métodos CRUD HATEOAS para la gestión de torneos")
public class TorneoControllerV2 {

    @Autowired
    private TorneoService torneoService;

    @Autowired
    private TorneoModelAssembler torneoModelAssembler;

    @GetMapping
    @Operation(summary = "Listado de todos los torneos")
    public ResponseEntity<CollectionModel<EntityModel<Torneo>>> findAll() {
        List<EntityModel<Torneo>> entityModels = this.torneoService.findAll()
                .stream()
                .map(torneoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Torneo>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(TorneoControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar torneo por ID")
    public ResponseEntity<EntityModel<Torneo>> findById(
            @Parameter(description = "ID del torneo", example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Torneo> entityModel = this.torneoModelAssembler.toModel(
                this.torneoService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/juego/{juegoId}")
    @Operation(summary = "Buscar torneos por juego")
    public ResponseEntity<List<Torneo>> findByJuegoId(@PathVariable Long juegoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.torneoService.findByJuegoId(juegoId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar torneos por estado")
    public ResponseEntity<List<Torneo>> findByEstado(@PathVariable String estado) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.torneoService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Guardar torneo")
    public ResponseEntity<EntityModel<Torneo>> save(@Valid @RequestBody TorneoDTO torneoDTO) {
        Torneo torneoCreate = this.torneoService.save(torneoDTO);
        EntityModel<Torneo> entityModel = this.torneoModelAssembler.toModel(torneoCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar torneo")
    public ResponseEntity<EntityModel<Torneo>> update(
            @PathVariable Long id,
            @Valid @RequestBody TorneoDTO torneoDTO
    ) {
        Torneo torneoUpdate = this.torneoService.updateById(id, torneoDTO);
        EntityModel<Torneo> entityModel = this.torneoModelAssembler.toModel(torneoUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar torneo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.torneoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}