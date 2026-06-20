package com.esports.user_service.services;

import com.esports.user_service.exceptions.UsuarioException;
import com.esports.user_service.models.Usuario;
import com.esports.user_service.models.dtos.UsuarioDTO;
import com.esports.user_service.repositories.UsuarioRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioPrueba;
    private UsuarioDTO usuarioDTO;
    private List<Usuario> usuarioList;

    @BeforeEach
    public void setUp() {
        usuarioPrueba = new Usuario();
        usuarioPrueba.setUsuarioId(1L);
        usuarioPrueba.setNombre("Daniel");
        usuarioPrueba.setNickname("danielsaa");
        usuarioPrueba.setEmail("daniel@gmail.com");
        usuarioPrueba.setPassword("password-encriptada");
        usuarioPrueba.setRol("JUGADOR");
        usuarioPrueba.setEstado("ACTIVO");
        usuarioPrueba.setFechaRegistro("2026-06-19");

        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Daniel");
        usuarioDTO.setNickname("danielsaa");
        usuarioDTO.setEmail("daniel@gmail.com");
        usuarioDTO.setPassword("123456");
        usuarioDTO.setRol("JUGADOR");
        usuarioDTO.setEstado("ACTIVO");
        usuarioDTO.setFechaRegistro("2026-06-19");

        usuarioList = new ArrayList<>();

        Faker faker = new Faker(Locale.of("es", "CL"));

        for (int i = 0; i < 100; i++) {
            Usuario usuario = new Usuario();
            usuario.setUsuarioId((long) i + 2);
            usuario.setNombre(faker.name().fullName());
            usuario.setNickname(faker.name().username());
            usuario.setEmail(faker.internet().emailAddress());
            usuario.setPassword("password-encriptada");
            usuario.setRol("JUGADOR");
            usuario.setEstado("ACTIVO");
            usuario.setFechaRegistro("2026-06-19");

            usuarioList.add(usuario);
        }

        usuarioList.add(usuarioPrueba);
    }

    @Test
    @DisplayName("Debe listar todos los usuarios")
    public void shouldFindAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(usuarioList);

        List<Usuario> result = usuarioService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(usuarioPrueba);
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar usuario por ID")
    public void shouldFindUsuarioById() {
        Long id = 1L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioPrueba));

        Usuario result = usuarioService.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getUsuarioId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("daniel@gmail.com");
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar usuario por ID inexistente")
    public void shouldNotFindUsuarioById() {
        Long id = 999L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.findById(id))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuario no encontrado");

        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe buscar usuario por nickname")
    public void shouldFindUsuarioByNickname() {
        String nickname = "danielsaa";
        when(usuarioRepository.findByNickname(nickname)).thenReturn(Optional.of(usuarioPrueba));

        Usuario result = usuarioService.findByNickname(nickname);

        assertThat(result).isNotNull();
        assertThat(result.getNickname()).isEqualTo(nickname);
        verify(usuarioRepository, times(1)).findByNickname(nickname);
    }

    @Test
    @DisplayName("Debe buscar usuario por email")
    public void shouldFindUsuarioByEmail() {
        String email = "daniel@gmail.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioPrueba));

        Usuario result = usuarioService.findByEmail(email);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("Debe guardar un usuario nuevo")
    public void shouldSaveUsuario() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNickname(usuarioDTO.getNickname())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuarioDTO.getPassword())).thenReturn("password-encriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioPrueba);

        Usuario result = usuarioService.save(usuarioDTO);

        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("daniel@gmail.com");
        assertThat(result.getNickname()).isEqualTo("danielsaa");

        verify(usuarioRepository, times(1)).findByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, times(1)).findByNickname(usuarioDTO.getNickname());
        verify(passwordEncoder, times(1)).encode(usuarioDTO.getPassword());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al guardar usuario con email registrado")
    public void shouldNotSaveUsuarioWhenEmailExists() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuarioPrueba));

        assertThatThrownBy(() -> usuarioService.save(usuarioDTO))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Correo ya registrado");

        verify(usuarioRepository, times(1)).findByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al guardar usuario con nickname registrado")
    public void shouldNotSaveUsuarioWhenNicknameExists() {
        when(usuarioRepository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.findByNickname(usuarioDTO.getNickname())).thenReturn(Optional.of(usuarioPrueba));

        assertThatThrownBy(() -> usuarioService.save(usuarioDTO))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Nickname ya registrado");

        verify(usuarioRepository, times(1)).findByEmail(usuarioDTO.getEmail());
        verify(usuarioRepository, times(1)).findByNickname(usuarioDTO.getNickname());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe actualizar usuario existente")
    public void shouldUpdateUsuarioById() {
        Long id = 1L;

        UsuarioDTO cambios = new UsuarioDTO();
        cambios.setNombre("Daniel Actualizado");
        cambios.setNickname("danielpro");
        cambios.setEmail("danielpro@gmail.com");
        cambios.setPassword("654321");
        cambios.setRol("ADMIN");
        cambios.setEstado("ACTIVO");
        cambios.setFechaRegistro("2026-06-20");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioPrueba));
        when(passwordEncoder.encode(cambios.getPassword())).thenReturn("password-encriptada-nueva");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Usuario result = usuarioService.updateById(id, cambios);

        assertThat(result.getNombre()).isEqualTo("Daniel Actualizado");
        assertThat(result.getNickname()).isEqualTo("danielpro");
        assertThat(result.getEmail()).isEqualTo("danielpro@gmail.com");
        assertThat(result.getPassword()).isEqualTo("password-encriptada-nueva");
        assertThat(result.getRol()).isEqualTo("ADMIN");

        verify(usuarioRepository, times(1)).findById(id);
        verify(passwordEncoder, times(1)).encode(cambios.getPassword());
        verify(usuarioRepository, times(1)).save(usuarioPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar usuario inexistente")
    public void shouldNotUpdateUsuarioWhenNotExists() {
        Long id = 999L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.updateById(id, usuarioDTO))
                .isInstanceOf(UsuarioException.class)
                .hasMessage("Usuario no encontrado");

        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe eliminar usuario por ID")
    public void shouldDeleteUsuarioById() {
        Long id = 1L;

        usuarioService.deleteById(id);

        verify(usuarioRepository, times(1)).deleteById(id);
    }
}