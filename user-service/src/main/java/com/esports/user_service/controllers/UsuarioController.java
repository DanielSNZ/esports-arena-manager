package com.esports.user_service.controllers;

import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.UsuarioDTO;
import com.esports.user_service.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findById(id));
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<Usuario> findByNickname(@PathVariable String nickname) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findByNickname(nickname));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(
            @Valid @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.save(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO usuarioDTO) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(usuarioService.updateById(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        usuarioService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}