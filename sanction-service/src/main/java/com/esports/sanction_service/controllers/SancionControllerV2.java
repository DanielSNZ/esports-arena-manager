package com.esports.sanction_service.controllers;

import com.esports.sanction_service.assemblers.SancionModelAssembler;
import com.esports.sanction_service.models.Sancion;
import com.esports.sanction_service.models.dtos.SancionDTO;
import com.esports.sanction_service.services.SancionService;
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
@RequestMapping("/api/v2/sanciones")
@Validated
@Tag(name = "Sanciones V2", description = "Métodos CRUD HATEOAS para la gestión de sanciones")
public class SancionControllerV2 {

    @Autowired
    private SancionService sancionService;

    @Autowired
    private SancionModelAssembler sancionModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todas las sanciones",
            description = "Se devuelve una colección HATEOAS con las sanciones de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Sancion>>> findAll() {
        List<EntityModel<Sancion>> entityModels = this.sancionService.findAll()
                .stream()
                .map(sancionModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Sancion>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(SancionControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de una sanción por id",
            description = "Se devuelve una sanción con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sanción encontrada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    public ResponseEntity<EntityModel<Sancion>> findById(
            @Parameter(description = "Id de la sanción a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Sancion> entityModel = this.sancionModelAssembler.toModel(
                this.sancionService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Búsqueda de sanciones por usuario", description = "Lista sanciones asociadas a un usuario")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Sancion>> findByUsuarioId(
            @Parameter(description = "Id del usuario", required = true, example = "1")
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.sancionService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Búsqueda de sanciones por tipo", description = "Lista sanciones filtradas por tipo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Sancion>> findByTipo(
            @Parameter(description = "Tipo de sanción", required = true, example = "ADVERTENCIA")
            @PathVariable String tipo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.sancionService.findByTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Búsqueda de sanciones por estado", description = "Lista sanciones filtradas por estado")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Sancion>> findByEstado(
            @Parameter(description = "Estado de la sanción", required = true, example = "ACTIVA")
            @PathVariable String estado
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.sancionService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Guardado de sanción", description = "Permite crear una nueva sanción")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Sanción a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = SancionDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Sanción creada")
    public ResponseEntity<EntityModel<Sancion>> save(@Valid @RequestBody SancionDTO sancionDTO) {
        Sancion sancionCreate = this.sancionService.save(sancionDTO);
        EntityModel<Sancion> entityModel = this.sancionModelAssembler.toModel(sancionCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de sanción", description = "Se actualizan los datos de una sanción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sanción actualizada"),
            @ApiResponse(responseCode = "404", description = "Sanción no encontrada")
    })
    public ResponseEntity<EntityModel<Sancion>> update(
            @Parameter(description = "Id de la sanción a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody SancionDTO sancionDTO
    ) {
        Sancion sancionUpdate = this.sancionService.updateById(id, sancionDTO);
        EntityModel<Sancion> entityModel = this.sancionModelAssembler.toModel(sancionUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de sanción", description = "Se elimina una sanción por su id")
    @ApiResponse(responseCode = "204", description = "Sanción eliminada")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la sanción a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.sancionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}