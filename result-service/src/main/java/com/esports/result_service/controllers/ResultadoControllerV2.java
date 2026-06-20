package com.esports.result_service.controllers;

import com.esports.result_service.assemblers.ResultadoModelAssembler;
import com.esports.result_service.models.Resultado;
import com.esports.result_service.models.dtos.ResultadoDTO;
import com.esports.result_service.services.ResultadoService;
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
@RequestMapping("/api/v2/resultados")
@Validated
@Tag(name = "Resultados V2", description = "Métodos CRUD HATEOAS para la gestión de resultados")
public class ResultadoControllerV2 {

    @Autowired
    private ResultadoService resultadoService;

    @Autowired
    private ResultadoModelAssembler resultadoModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todos los resultados",
            description = "Se devuelve una colección HATEOAS con los resultados de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Resultado>>> findAll() {
        List<EntityModel<Resultado>> entityModels = this.resultadoService.findAll()
                .stream()
                .map(resultadoModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Resultado>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(ResultadoControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de un resultado por id",
            description = "Se devuelve un resultado con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado encontrado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    public ResponseEntity<EntityModel<Resultado>> findById(
            @Parameter(description = "Id del resultado a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Resultado> entityModel = this.resultadoModelAssembler.toModel(
                this.resultadoService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/partida/{partidaId}")
    @Operation(summary = "Búsqueda de resultados por partida", description = "Lista resultados asociados a una partida")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Resultado>> findByPartidaId(
            @Parameter(description = "Id de la partida", required = true, example = "1")
            @PathVariable Long partidaId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.resultadoService.findByPartidaId(partidaId));
    }

    @GetMapping("/ganador/{ganadorId}")
    @Operation(summary = "Búsqueda de resultados por ganador", description = "Lista resultados asociados a un ganador")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Resultado>> findByGanadorId(
            @Parameter(description = "Id del ganador", required = true, example = "1")
            @PathVariable Long ganadorId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.resultadoService.findByGanadorId(ganadorId));
    }

    @GetMapping("/estado/{estadoValidacion}")
    @Operation(summary = "Búsqueda de resultados por estado de validación", description = "Lista resultados filtrados por estado de validación")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Resultado>> findByEstadoValidacion(
            @Parameter(description = "Estado de validación", required = true, example = "VALIDADO")
            @PathVariable String estadoValidacion
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.resultadoService.findByEstadoValidacion(estadoValidacion));
    }

    @PostMapping
    @Operation(summary = "Guardado de resultado", description = "Permite crear un nuevo resultado")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Resultado a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = ResultadoDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Resultado creado")
    public ResponseEntity<EntityModel<Resultado>> save(@Valid @RequestBody ResultadoDTO resultadoDTO) {
        Resultado resultadoCreate = this.resultadoService.save(resultadoDTO);
        EntityModel<Resultado> entityModel = this.resultadoModelAssembler.toModel(resultadoCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de resultado", description = "Se actualizan los datos de un resultado existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado actualizado"),
            @ApiResponse(responseCode = "404", description = "Resultado no encontrado")
    })
    public ResponseEntity<EntityModel<Resultado>> update(
            @Parameter(description = "Id del resultado a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ResultadoDTO resultadoDTO
    ) {
        Resultado resultadoUpdate = this.resultadoService.updateById(id, resultadoDTO);
        EntityModel<Resultado> entityModel = this.resultadoModelAssembler.toModel(resultadoUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de resultado", description = "Se elimina un resultado por su id")
    @ApiResponse(responseCode = "204", description = "Resultado eliminado")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id del resultado a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.resultadoService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}