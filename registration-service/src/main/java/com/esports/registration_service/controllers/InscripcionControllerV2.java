package com.esports.registration_service.controllers;

import com.esports.registration_service.assemblers.InscripcionModelAssembler;
import com.esports.registration_service.models.Inscripcion;
import com.esports.registration_service.models.dtos.InscripcionDTO;
import com.esports.registration_service.services.InscripcionService;
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

/**
 * Controlador REST versión 2 para la gestión de inscripciones.
 *
 * Expone operaciones CRUD devolviendo representaciones HATEOAS
 * mediante EntityModel y CollectionModel.
 */
@RestController
@RequestMapping("/api/v2/inscripciones")
@Validated
@Tag(name = "Inscripciones V2", description = "Métodos CRUD HATEOAS para la gestión de inscripciones")
public class InscripcionControllerV2 {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionModelAssembler inscripcionModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todas las inscripciones",
            description = "Se devuelve una colección HATEOAS con las inscripciones de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Inscripcion>>> findAll() {
        List<EntityModel<Inscripcion>> entityModels = this.inscripcionService.findAll()
                .stream()
                .map(inscripcionModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Inscripcion>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(InscripcionControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de una inscripción por id",
            description = "Se devuelve una inscripción con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<EntityModel<Inscripcion>> findById(
            @Parameter(description = "Id de la inscripción a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Inscripcion> entityModel = this.inscripcionModelAssembler.toModel(
                this.inscripcionService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/torneo/{torneoId}")
    @Operation(summary = "Búsqueda de inscripciones por torneo", description = "Lista inscripciones asociadas a un torneo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Inscripcion>> findByTorneoId(
            @Parameter(description = "Id del torneo", required = true, example = "1")
            @PathVariable Long torneoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inscripcionService.findByTorneoId(torneoId));
    }

    @GetMapping("/equipo/{equipoId}")
    @Operation(summary = "Búsqueda de inscripciones por equipo", description = "Lista inscripciones asociadas a un equipo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Inscripcion>> findByEquipoId(
            @Parameter(description = "Id del equipo", required = true, example = "1")
            @PathVariable Long equipoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inscripcionService.findByEquipoId(equipoId));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Búsqueda de inscripciones por estado", description = "Lista inscripciones filtradas por estado")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Inscripcion>> findByEstado(
            @Parameter(description = "Estado de la inscripción", required = true, example = "CONFIRMADA")
            @PathVariable String estado
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inscripcionService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Guardado de inscripción", description = "Permite crear una nueva inscripción")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Inscripción a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = InscripcionDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Inscripción creada")
    public ResponseEntity<EntityModel<Inscripcion>> save(@Valid @RequestBody InscripcionDTO inscripcionDTO) {
        Inscripcion inscripcionCreate = this.inscripcionService.save(inscripcionDTO);
        EntityModel<Inscripcion> entityModel = this.inscripcionModelAssembler.toModel(inscripcionCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de inscripción", description = "Se actualizan los datos de una inscripción existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inscripción actualizada"),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada")
    })
    public ResponseEntity<EntityModel<Inscripcion>> update(
            @Parameter(description = "Id de la inscripción a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody InscripcionDTO inscripcionDTO
    ) {
        Inscripcion inscripcionUpdate = this.inscripcionService.updateById(id, inscripcionDTO);
        EntityModel<Inscripcion> entityModel = this.inscripcionModelAssembler.toModel(inscripcionUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de inscripción", description = "Se elimina una inscripción por su id")
    @ApiResponse(responseCode = "204", description = "Inscripción eliminada")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la inscripción a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.inscripcionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}