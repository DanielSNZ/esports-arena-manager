package com.esports.user_service.services;

import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<Usuario> findAll();

    Usuario findById(Long id);

    Usuario findByNickname(String nickname);

    Usuario findByEmail(String email);

    Usuario save(UsuarioDTO usuarioDTO);

    Usuario updateById(Long id, UsuarioDTO usuarioDTO);

    void deleteById(Long id);
}