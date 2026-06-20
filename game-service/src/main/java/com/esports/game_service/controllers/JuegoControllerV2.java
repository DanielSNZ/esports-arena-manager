package com.esports.game_service.controllers;

import com.esports.game_service.assemblers.JuegoModelAssembler;
import com.esports.game_service.models.Juego;
import com.esports.game_service.models.dtos.JuegoDTO;
import com.esports.game_service.services.JuegoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
@RequestMapping("/api/v2/juegos")
@Validated
@Tag(name="Juegos V2", description = "Métodos CRUD HATEOAS para la gestión de juegos")
public class JuegoControllerV2 {

    @Autowired
    private JuegoService juegoService;

    @Autowired
    private JuegoModelAssembler juegoModelAssembler;

    @GetMapping
    @Operation(summary = "Listado de todos los juegos")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Juego>>> findAll() {

        List<EntityModel<Juego>> entityModels = this.juegoService.findAll()
                .stream()
                .map(juegoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Juego>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(JuegoControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar juego por ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Juego encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JuegoDTO.class),
                            examples = {
                                    @ExampleObject(
                                            value = "{\"nombre\":\"Counter Strike 2\",\"genero\":\"FPS\",\"modalidad\":\"5v5\",\"jugadoresPorEquipo\":5,\"estado\":\"ACTIVO\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    public ResponseEntity<EntityModel<Juego>> findById(
            @Parameter(description = "ID del juego", example = "1")
            @PathVariable Long id
    ) {

        EntityModel<Juego> entityModel = this.juegoModelAssembler.toModel(
                this.juegoService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @Operation(summary = "Guardar juego")
    public ResponseEntity<EntityModel<Juego>> save(@Valid @RequestBody JuegoDTO juegoDTO) {

        Juego juego = this.juegoService.save(juegoDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(juegoModelAssembler.toModel(juego));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar juego")
    public ResponseEntity<EntityModel<Juego>> update(
            @PathVariable Long id,
            @Valid @RequestBody JuegoDTO juegoDTO
    ) {

        Juego juego = this.juegoService.updateById(id, juegoDTO);

        return ResponseEntity.ok(juegoModelAssembler.toModel(juego));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar juego")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        this.juegoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}