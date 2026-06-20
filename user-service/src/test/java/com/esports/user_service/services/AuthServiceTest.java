package com.esports.user_service.services;

import com.esports.user_service.exceptions.UsuarioException;
import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.AuthResponse;
import com.esports.user_service.models.dtos.LoginRequest;
import com.esports.user_service.models.dtos.RegisterRequest;
import com.esports.user_service.repositories.UsuarioRepository;
import com.esports.user_service.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;
    private LoginRequest loginRequest;
    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setUsuarioId(1L);
        usuario.setNombre("Daniel Saa");
        usuario.setNickname("DanielSNZ");
        usuario.setEmail("daniel@gmail.com");
        usuario.setPassword("password-encriptada");
        usuario.setRol("ADMIN");
        usuario.setEstado("ACTIVO");
        usuario.setFechaRegistro("2026-06-20");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("daniel@gmail.com");
        loginRequest.setPassword("123456");

        registerRequest = new RegisterRequest();
        registerRequest.setNombre("Daniel Saa");
        registerRequest.setNickname("DanielSNZ");
        registerRequest.setEmail("daniel@gmail.com");
        registerRequest.setPassword("123456");
        registerRequest.setRol("ADMIN");
        registerRequest.setEstado("ACTIVO");
        registerRequest.setFechaRegistro("2026-06-20");
    }

    @Test
    @DisplayName("Debe registrar usuario y devolver token")
    public void shouldRegisterUser() {
        when(usuarioRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNickname(registerRequest.getNickname())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("password-encriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtService.generarToken(any(Usuario.class))).thenReturn("token-jwt");

        AuthResponse result = authService.register(registerRequest);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("token-jwt");
        assertThat(result.getTipo()).isEqualTo("Bearer");
        assertThat(result.getEmail()).isEqualTo("daniel@gmail.com");
        assertThat(result.getRol()).isEqualTo("ADMIN");

        verify(usuarioRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(usuarioRepository, times(1)).findByNickname(registerRequest.getNickname());
        verify(passwordEncoder, times(1)).encode(registerRequest.getPassword());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(jwtService, times(1)).generarToken(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al registrar email existente")
    public void shouldNotRegisterWhenEmailExists() {
        when(usuarioRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Correo ya registrado");

        verify(usuarioRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al registrar nickname existente")
    public void shouldNotRegisterWhenNicknameExists() {
        when(usuarioRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNickname(registerRequest.getNickname())).thenReturn(Optional.of(usuario));

        assertThatThrownBy(() -> authService.register(registerRequest))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Nickname ya registrado");

        verify(usuarioRepository, times(1)).findByEmail(registerRequest.getEmail());
        verify(usuarioRepository, times(1)).findByNickname(registerRequest.getNickname());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe iniciar sesión correctamente")
    public void shouldLoginUser() {
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())).thenReturn(true);
        when(jwtService.generarToken(usuario)).thenReturn("token-jwt");

        AuthResponse result = authService.login(loginRequest);

        assertThat(result).isNotNull();
        assertThat(result.getToken()).isEqualTo("token-jwt");
        assertThat(result.getTipo()).isEqualTo("Bearer");
        assertThat(result.getEmail()).isEqualTo("daniel@gmail.com");
        assertThat(result.getRol()).isEqualTo("ADMIN");

        verify(usuarioRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), usuario.getPassword());
        verify(jwtService, times(1)).generarToken(usuario);
    }

    @Test
    @DisplayName("Debe lanzar excepción al iniciar sesión con email inexistente")
    public void shouldNotLoginWhenEmailDoesNotExist() {
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(ResponseStatusException.class);

        verify(usuarioRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al iniciar sesión con contraseña incorrecta")
    public void shouldNotLoginWhenPasswordIsWrong() {
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(ResponseStatusException.class);

        verify(usuarioRepository, times(1)).findByEmail(loginRequest.getEmail());
        verify(passwordEncoder, times(1)).matches(loginRequest.getPassword(), usuario.getPassword());
        verify(jwtService, never()).generarToken(any(Usuario.class));
    }
}