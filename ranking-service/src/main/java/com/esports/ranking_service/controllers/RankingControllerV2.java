package com.esports.ranking_service.controllers;

import com.esports.ranking_service.assemblers.RankingModelAssembler;
import com.esports.ranking_service.models.Ranking;
import com.esports.ranking_service.models.dtos.RankingDTO;
import com.esports.ranking_service.services.RankingService;
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
@RequestMapping("/api/v2/rankings")
@Validated
@Tag(name = "Rankings V2", description = "Métodos CRUD HATEOAS para la gestión de rankings")
public class RankingControllerV2 {

    @Autowired
    private RankingService rankingService;

    @Autowired
    private RankingModelAssembler rankingModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de todos los rankings",
            description = "Se devuelve una colección HATEOAS con los rankings de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Ranking>>> findAll() {
        List<EntityModel<Ranking>> entityModels = this.rankingService.findAll()
                .stream()
                .map(rankingModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Ranking>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(RankingControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity.status(HttpStatus.OK).body(collectionModel);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de un ranking por id",
            description = "Se devuelve un ranking con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking encontrado"),
            @ApiResponse(responseCode = "404", description = "Ranking no encontrado")
    })
    public ResponseEntity<EntityModel<Ranking>> findById(
            @Parameter(description = "Id del ranking a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Ranking> entityModel = this.rankingModelAssembler.toModel(
                this.rankingService.findById(id)
        );

        return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/equipo/{equipoId}")
    @Operation(summary = "Búsqueda de rankings por equipo", description = "Lista rankings asociados a un equipo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Ranking>> findByEquipoId(
            @Parameter(description = "Id del equipo", required = true, example = "1")
            @PathVariable Long equipoId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.rankingService.findByEquipoId(equipoId));
    }

    @GetMapping("/torneo/{torneoId}")
    @Operation(summary = "Búsqueda de rankings por torneo", description = "Lista rankings asociados a un torneo")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Ranking>> findByTorneoId(
            @Parameter(description = "Id del torneo", required = true, example = "1")
            @PathVariable Long torneoId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.rankingService.findByTorneoId(torneoId));
    }

    @GetMapping("/posicion/{posicion}")
    @Operation(summary = "Búsqueda de rankings por posición", description = "Lista rankings filtrados por posición")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<List<Ranking>> findByPosicion(
            @Parameter(description = "Posición del ranking", required = true, example = "1")
            @PathVariable Integer posicion
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.rankingService.findByPosicion(posicion));
    }

    @PostMapping
    @Operation(summary = "Guardado de ranking", description = "Permite crear un nuevo ranking")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Ranking a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = RankingDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Ranking creado")
    public ResponseEntity<EntityModel<Ranking>> save(@Valid @RequestBody RankingDTO rankingDTO) {
        Ranking rankingCreate = this.rankingService.save(rankingDTO);
        EntityModel<Ranking> entityModel = this.rankingModelAssembler.toModel(rankingCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(entityModel);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualización de ranking", description = "Se actualizan los datos de un ranking existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking actualizado"),
            @ApiResponse(responseCode = "404", description = "Ranking no encontrado")
    })
    public ResponseEntity<EntityModel<Ranking>> update(
            @Parameter(description = "Id del ranking a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RankingDTO rankingDTO
    ) {
        Ranking rankingUpdate = this.rankingService.updateById(id, rankingDTO);
        EntityModel<Ranking> entityModel = this.rankingModelAssembler.toModel(rankingUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de ranking", description = "Se elimina un ranking por su id")
    @ApiResponse(responseCode = "204", description = "Ranking eliminado")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id del ranking a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.rankingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}