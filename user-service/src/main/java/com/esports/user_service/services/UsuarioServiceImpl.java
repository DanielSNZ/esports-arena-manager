package com.esports.user_service.services;

import com.esports.user_service.exceptions.UsuarioException;
import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.UsuarioDTO;
import com.esports.user_service.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> findAll() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario findById(Long id) {
        return this.usuarioRepository.findById(id).orElseThrow(
                () -> new UsuarioException("Usuario no encontrado")
        );
    }

    @Override
    public Usuario findByNickname(String nickname) {
        return this.usuarioRepository.findByNickname(nickname).orElseThrow(
                () -> new UsuarioException("Usuario no encontrado")
        );
    }

    @Override
    public Usuario findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email).orElseThrow(
                () -> new UsuarioException("Usuario no encontrado")
        );
    }

    @Override
    public Usuario save(UsuarioDTO usuarioDTO) {

        if (this.usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new UsuarioException("Correo ya registrado");
        }

        if (this.usuarioRepository.findByNickname(usuarioDTO.getNickname()).isPresent()) {
            throw new UsuarioException("Nickname ya registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setNickname(usuarioDTO.getNickname());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setRol(usuarioDTO.getRol());
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());

        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateById(Long id, UsuarioDTO usuarioDTO) {

        return this.usuarioRepository.findById(id).map(usuario -> {

            usuario.setNombre(usuarioDTO.getNombre());
            usuario.setNickname(usuarioDTO.getNickname());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            usuario.setRol(usuarioDTO.getRol());
            usuario.setEstado(usuarioDTO.getEstado());
            usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());

            return this.usuarioRepository.save(usuario);

        }).orElseThrow(
                () -> new UsuarioException("Usuario no encontrado")
        );
    }

    @Override
    public void deleteById(Long id) {
        this.usuarioRepository.deleteById(id);
    }
}