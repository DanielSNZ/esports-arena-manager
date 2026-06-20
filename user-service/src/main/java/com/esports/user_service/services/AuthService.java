package com.esports.user_service.services;

import com.esports.user_service.exceptions.UsuarioException;
import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.AuthResponse;
import com.esports.user_service.models.dtos.LoginRequest;
import com.esports.user_service.models.dtos.RegisterRequest;
import com.esports.user_service.repositories.UsuarioRepository;
import com.esports.user_service.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (this.usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsuarioException("Correo ya registrado");
        }

        if (this.usuarioRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new UsuarioException("Nickname ya registrado");
        }

        Usuario usuario = new Usuario();

        usuario.setNombre(request.getNombre());
        usuario.setNickname(request.getNickname());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(request.getRol());
        usuario.setEstado(request.getEstado());
        usuario.setFechaRegistro(request.getFechaRegistro());

        this.usuarioRepository.save(usuario);

        return construirRespuesta(usuario);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = this.usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Credenciales inválidas"
                ));

        if (!this.passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Credenciales inválidas"
            );
        }

        return construirRespuesta(usuario);
    }

    private AuthResponse construirRespuesta(Usuario usuario) {
        String token = this.jwtService.generarToken(usuario);

        return new AuthResponse(
                token,
                "Bearer",
                usuario.getEmail(),
                usuario.getRol()
        );
    }
}