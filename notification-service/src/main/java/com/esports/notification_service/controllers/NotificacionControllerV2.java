package com.esports.notification_service.controllers;

import com.esports.notification_service.assemblers.NotificacionModelAssembler;
import com.esports.notification_service.models.Notificacion;
import com.esports.notification_service.models.dtos.NotificacionDTO;
import com.esports.notification_service.services.NotificacionService;
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
@RequestMapping("/api/v2/notificaciones")
@Validated
@Tag(name = "Notificaciones V2", description = "Métodos CRUD HATEOAS para la gestión de notificaciones")
public class NotificacionControllerV2 {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private NotificacionModelAssembler notificacionModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todas las notificaciones",
            description = "Se devuelve una colección HATEOAS con las notificaciones de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Notificacion>>> findAll() {
        List<EntityModel<Notificacion>> entityModels = this.notificacionService.findAll()
                .stream()
                .map(notificacionModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Notificacion>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(NotificacionControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de una notificación por id",
            description = "Se devuelve una notificación con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<EntityModel<Notificacion>> findById(
            @Parameter(description = "Id de la notificación a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Notificacion> entityModel = this.notificacionModelAssembler.toModel(
                this.notificacionService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Búsqueda de notificaciones por usuario", description = "Lista notificaciones asociadas a un usuario")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Notificacion>> findByUsuarioId(
            @Parameter(description = "Id del usuario", required = true, example = "1")
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notificacionService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Búsqueda de notificaciones por tipo", description = "Lista notificaciones filtradas por tipo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Notificacion>> findByTipo(
            @Parameter(description = "Tipo de notificación", required = true, example = "INFO")
            @PathVariable String tipo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notificacionService.findByTipo(tipo));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Búsqueda de notificaciones por estado", description = "Lista notificaciones filtradas por estado")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Notificacion>> findByEstado(
            @Parameter(description = "Estado de la notificación", required = true, example = "ENVIADA")
            @PathVariable String estado
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.notificacionService.findByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Guardado de notificación", description = "Permite crear una nueva notificación")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Notificación a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = NotificacionDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Notificación creada")
    public ResponseEntity<EntityModel<Notificacion>> save(@Valid @RequestBody NotificacionDTO notificacionDTO) {
        Notificacion notificacionCreate = this.notificacionService.save(notificacionDTO);
        EntityModel<Notificacion> entityModel = this.notificacionModelAssembler.toModel(notificacionCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de notificación", description = "Se actualizan los datos de una notificación existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación actualizada"),
            @ApiResponse(responseCode = "404", description = "Notificación no encontrada")
    })
    public ResponseEntity<EntityModel<Notificacion>> update(
            @Parameter(description = "Id de la notificación a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody NotificacionDTO notificacionDTO
    ) {
        Notificacion notificacionUpdate = this.notificacionService.updateById(id, notificacionDTO);
        EntityModel<Notificacion> entityModel = this.notificacionModelAssembler.toModel(notificacionUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de notificación", description = "Se elimina una notificación por su id")
    @ApiResponse(responseCode = "204", description = "Notificación eliminada")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id de la notificación a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.notificacionService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}