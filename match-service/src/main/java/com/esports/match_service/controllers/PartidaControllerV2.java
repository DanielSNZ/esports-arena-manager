package com.esports.match_service.controllers;

import com.esports.match_service.assemblers.PartidaModelAssembler;
import com.esports.match_service.models.Partida;
import com.esports.match_service.models.dtos.PartidaDTO;
import com.esports.match_service.services.PartidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v2/partidas")
@Validated
@Tag(name = "Partidas V2", description = "Métodos CRUD HATEOAS para la gestión de partidas")
public class PartidaControllerV2 {

    @Autowired
    private PartidaService partidaService;

    @Autowired
    private PartidaModelAssembler partidaModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todas las partidas",
            description = "Se devuelve una colección HATEOAS con las partidas de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Partida>>> findAll() {
        List<EntityModel<Partida>> entityModels = this.partidaService.findAll()
                .stream()
                .map(partidaModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Partida>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(PartidaControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de una partida por id",
            description = "Se devuelve una partida con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida encontrada"),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada")
    })
    public ResponseEntity<EntityModel<Partida>> findById(
            @Parameter(description = "Id de la partida a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Partida> entityModel = this.partidaModelAssembler.toModel(
                this.partidaService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/torneo/{torneoId}")
    @Operation(summary = "Búsqueda de partidas por torneo", description = "Lista partidas asociadas a un torneo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Partida>> findByTorneoId(
            @Parameter(description = "Id del torneo", required = true, example = "1")
            @PathVariable Long torneoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.partidaService.findByTorneoId(torneoId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Búsqueda de partidas por estado", description = "Lista partidas filtradas por estado")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Partida>> findByEstado(
            @Parameter(description = "Estado de la partida", required = true, example = "PROGRAMADA")
            @PathVariable String estado
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.partidaService.findByEstado(estado));
    }

    @GetMapping("/ganador/{ganadorId}")
    @Operation(summary = "Búsqueda de partidas por ganador", description = "Lista partidas ganadas por un equipo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Partida>> findByGanadorId(
            @Parameter(description = "Id del equipo ganador", required = true, example = "1")
            @PathVariable Long ganadorId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.partidaService.findByGanadorId(ganadorId));
    }

    @PostMapping
    @Operation(summary = "Guardado de partida", description = "Permite crear una nueva partida")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Partida a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = PartidaDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Partida creada")
    public ResponseEntity<EntityModel<Partida>> save(@Valid @RequestBody PartidaDTO partidaDTO) {
        Partida partidaCreate = this.partidaService.save(partidaDTO);
        EntityModel<Partida> entityModel = this.partidaModelAssembler.toModel(partidaCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de partida", description = "Se actualizan los datos de una partida existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Partida actualizada"),
            @ApiResponse(responseCode = "404", description = "Partida no encontrada")
    })
    public ResponseEntity<EntityModel<Partida>> update(
            @Parameter(description = "Id de la partida a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody PartidaDTO partidaDTO
    ) {
        Partida partidaUpdate = this.partidaService.updateById(id, partidaDTO);
        EntityModel<Partida> entityModel = this.partidaModelAssembler.toModel(partidaUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de partida", description = "Se elimina una partida por su id")
    @ApiResponse(responseCode = "204", description = "Partida eliminada")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la partida a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.partidaService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}