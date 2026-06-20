package com.esports.user_service.controllers;

import com.esports.user_service.assemblers.UsuarioModelAssembler;
import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.UsuarioDTO;
import com.esports.user_service.services.UsuarioService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios")
@Validated
@Tag(name = "Usuarios V2", description = "Métodos CRUD HATEOAS para la gestión de usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @PreAuthorize("hasAnyAuthority('ADMIN','JUGADOR')")
    @GetMapping
    @Operation(
            summary = "Listado de todos los usuarios",
            description = "Se devuelve una colección HATEOAS con los usuarios de la base de datos"
    )
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> findAll() {
        List<EntityModel<Usuario>> entityModels = this.usuarioService.findAll()
                .stream()
                .map(usuarioModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(UsuarioControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','JUGADOR')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Búsqueda de un usuario por id",
            description = "Se devuelve un usuario con enlaces HATEOAS; en caso contrario se devuelve una excepción"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo Usuario",
                                            value = "{\"nombre\":\"Daniel Saa\",\"nickname\":\"DanielSNZ\",\"email\":\"daniel@gmail.com\",\"rol\":\"ADMIN\",\"estado\":\"ACTIVO\",\"fechaRegistro\":\"2026-06-19\"}"
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> findById(
            @Parameter(description = "Id del usuario a buscar", required = true, example = "1")
            @PathVariable Long id
    ) {
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(
                this.usuarioService.findById(id)
        );
        return ResponseEntity.ok(entityModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    @Operation(summary = "Guardado de usuario", description = "Permite guardar un usuario")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Usuario a crear",
            required = true,
            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
    )
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    public ResponseEntity<EntityModel<Usuario>> save(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioCreate = this.usuarioService.save(usuarioDTO);
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(usuarioCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entityModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualización de usuario", description = "Se actualizan los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public ResponseEntity<EntityModel<Usuario>> update(
            @Parameter(description = "Id del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO
    ) {
        Usuario usuarioUpdate = this.usuarioService.updateById(id, usuarioDTO);
        EntityModel<Usuario> entityModel = this.usuarioModelAssembler.toModel(usuarioUpdate);

        return ResponseEntity.ok(entityModel);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminación de usuario", description = "Se elimina un usuario por su id")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Id del usuario a eliminar", required = true, example = "1")
            @PathVariable Long id
    ) {
        this.usuarioService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}