package com.esports.team_service.controllers;

import com.esports.team_service.assemblers.EquipoModelAssembler;
import com.esports.team_service.models.Equipo;
import com.esports.team_service.models.dtos.EquipoDTO;
import com.esports.team_service.services.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v2/equipos")
@Validated
@Tag(name = "Equipos V2", description = "Métodos CRUD HATEOAS para la gestión de equipos")
public class EquipoControllerV2 {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private EquipoModelAssembler equipoModelAssembler;

    @GetMapping
    @Operation(summary = "Listado de todos los equipos")
    public ResponseEntity<CollectionModel<EntityModel<Equipo>>> findAll() {
        List<EntityModel<Equipo>> entityModels = this.equipoService.findAll()
                .stream()
                .map(equipoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Equipo>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(EquipoControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipo por ID")
    public ResponseEntity<EntityModel<Equipo>> findById(@PathVariable Long id) {
        EntityModel<Equipo> entityModel = this.equipoModelAssembler.toModel(
                this.equipoService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/juego/{juegoPrincipalId}")
    @Operation(summary = "Buscar equipos por juego principal")
    public ResponseEntity<List<Equipo>> findByJuegoPrincipalId(@PathVariable Long juegoPrincipalId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.equipoService.findByJuegoPrincipalId(juegoPrincipalId));
    }

    @GetMapping("/capitan/{capitanId}")
    @Operation(summary = "Buscar equipos por capitán")
    public ResponseEntity<List<Equipo>> findByCapitanId(@PathVariable Long capitanId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.equipoService.findByCapitanId(capitanId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar equipos por estado")
    public ResponseEntity<List<Equipo>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.equipoService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Guardar equipo")
    public ResponseEntity<EntityModel<Equipo>> save(@Valid @RequestBody EquipoDTO equipoDTO) {
        Equipo equipoCreate = this.equipoService.save(equipoDTO);
        EntityModel<Equipo> entityModel = this.equipoModelAssembler.toModel(equipoCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar equipo")
    public ResponseEntity<EntityModel<Equipo>> update(
            @PathVariable Long id,
            @Valid @RequestBody EquipoDTO equipoDTO) {

        Equipo equipoUpdate = this.equipoService.updateById(id, equipoDTO);
        EntityModel<Equipo> entityModel = this.equipoModelAssembler.toModel(equipoUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar equipo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.equipoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}