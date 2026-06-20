package com.esports.tournament_service.services;

import com.esports.tournament_service.clients.GameClient;
import com.esports.tournament_service.exceptions.TorneoException;
import com.esports.tournament_service.models.Torneo;
import com.esports.tournament_service.models.dtos.GameResponseDTO;
import com.esports.tournament_service.models.dtos.TorneoDTO;
import com.esports.tournament_service.repositories.TorneoRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TorneoServiceTest {

    @Mock
    private TorneoRepository torneoRepository;

    @Mock
    private GameClient gameClient;

    @InjectMocks
    private TorneoServiceImpl torneoService;

    private Torneo torneoPrueba;
    private TorneoDTO torneoDTO;
    private GameResponseDTO juegoActivo;
    private GameResponseDTO juegoInactivo;
    private List<Torneo> torneoList;

    @BeforeEach
    public void setUp() {
        torneoPrueba = new Torneo();
        torneoPrueba.setTorneoId(1L);
        torneoPrueba.setNombre("Torneo CS2");
        torneoPrueba.setJuegoId(1L);
        torneoPrueba.setFechaInicio(LocalDate.of(2026, 6, 20));
        torneoPrueba.setFechaFin(LocalDate.of(2026, 6, 25));
        torneoPrueba.setCupoMaximo(16);
        torneoPrueba.setEstado("ACTIVO");
        torneoPrueba.setModalidad("5v5");

        torneoDTO = new TorneoDTO();
        torneoDTO.setNombre("Torneo CS2");
        torneoDTO.setJuegoId(1L);
        torneoDTO.setFechaInicio(LocalDate.of(2026, 6, 20));
        torneoDTO.setFechaFin(LocalDate.of(2026, 6, 25));
        torneoDTO.setCupoMaximo(16);
        torneoDTO.setEstado("ACTIVO");
        torneoDTO.setModalidad("5v5");

        juegoActivo = new GameResponseDTO();
        juegoActivo.setJuegoId(1L);
        juegoActivo.setNombre("Counter Strike 2");
        juegoActivo.setEstado("ACTIVO");

        juegoInactivo = new GameResponseDTO();
        juegoInactivo.setJuegoId(2L);
        juegoInactivo.setNombre("Juego Inactivo");
        juegoInactivo.setEstado("INACTIVO");

        torneoList = new ArrayList<>();

        Faker faker = new Faker(Locale.of("es", "CL"));

        for (int i = 0; i < 100; i++) {
            Torneo torneo = new Torneo();
            torneo.setTorneoId((long) i + 2);
            torneo.setNombre("Torneo " + faker.esports().game());
            torneo.setJuegoId(1L);
            torneo.setFechaInicio(LocalDate.of(2026, 7, 1));
            torneo.setFechaFin(LocalDate.of(2026, 7, 10));
            torneo.setCupoMaximo(16);
            torneo.setEstado("ACTIVO");
            torneo.setModalidad("5v5");

            torneoList.add(torneo);
        }

        torneoList.add(torneoPrueba);
    }

    @Test
    @DisplayName("Debe listar todos los torneos")
    public void shouldFindAllTorneos() {
        when(torneoRepository.findAll()).thenReturn(torneoList);

        List<Torneo> result = torneoService.findAll();

        assertThat(result).hasSize(101);
        assertThat(result).contains(torneoPrueba);
        verify(torneoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debe buscar torneo por ID")
    public void shouldFindTorneoById() {
        Long id = 1L;
        when(torneoRepository.findById(id)).thenReturn(Optional.of(torneoPrueba));

        Torneo result = torneoService.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getTorneoId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Torneo CS2");
        verify(torneoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al buscar torneo inexistente")
    public void shouldNotFindTorneoById() {
        Long id = 999L;
        when(torneoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> torneoService.findById(id))
                .isInstanceOf(TorneoException.class)
                .hasMessage("Torneo no encontrado");

        verify(torneoRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Debe buscar torneos por juegoId")
    public void shouldFindTorneosByJuegoId() {
        Long juegoId = 1L;
        when(torneoRepository.findByJuegoId(juegoId)).thenReturn(torneoList);

        List<Torneo> result = torneoService.findByJuegoId(juegoId);

        assertThat(result).hasSize(101);
        assertThat(result.get(0).getJuegoId()).isEqualTo(juegoId);
        verify(torneoRepository, times(1)).findByJuegoId(juegoId);
    }

    @Test
    @DisplayName("Debe buscar torneos por estado")
    public void shouldFindTorneosByEstado() {
        String estado = "ACTIVO";
        when(torneoRepository.findByEstado(estado)).thenReturn(torneoList);

        List<Torneo> result = torneoService.findByEstado(estado);

        assertThat(result).hasSize(101);
        assertThat(result.get(0).getEstado()).isEqualTo(estado);
        verify(torneoRepository, times(1)).findByEstado(estado);
    }

    @Test
    @DisplayName("Debe guardar torneo si el juego está activo")
    public void shouldSaveTorneoWhenGameIsActive() {
        when(gameClient.findById(torneoDTO.getJuegoId())).thenReturn(juegoActivo);
        when(torneoRepository.save(any(Torneo.class))).thenReturn(torneoPrueba);

        Torneo result = torneoService.save(torneoDTO);

        assertThat(result).isNotNull();
        assertThat(result.getNombre()).isEqualTo("Torneo CS2");
        assertThat(result.getJuegoId()).isEqualTo(1L);

        verify(gameClient, times(1)).findById(torneoDTO.getJuegoId());
        verify(torneoRepository, times(1)).save(any(Torneo.class));
    }

    @Test
    @DisplayName("Debe lanzar excepción al guardar torneo si el juego no está activo")
    public void shouldNotSaveTorneoWhenGameIsInactive() {
        when(gameClient.findById(torneoDTO.getJuegoId())).thenReturn(juegoInactivo);

        assertThatThrownBy(() -> torneoService.save(torneoDTO))
                .isInstanceOf(TorneoException.class)
                .hasMessage("El juego no está activo");

        verify(gameClient, times(1)).findById(torneoDTO.getJuegoId());
        verify(torneoRepository, never()).save(any(Torneo.class));
    }

    @Test
    @DisplayName("Debe actualizar torneo existente")
    public void shouldUpdateTorneoById() {
        Long id = 1L;

        TorneoDTO cambios = new TorneoDTO();
        cambios.setNombre("Torneo Actualizado");
        cambios.setJuegoId(1L);
        cambios.setFechaInicio(LocalDate.of(2026, 8, 1));
        cambios.setFechaFin(LocalDate.of(2026, 8, 5));
        cambios.setCupoMaximo(32);
        cambios.setEstado("ACTIVO");
        cambios.setModalidad("1v1");

        when(torneoRepository.findById(id)).thenReturn(Optional.of(torneoPrueba));
        when(torneoRepository.save(any(Torneo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Torneo result = torneoService.updateById(id, cambios);

        assertThat(result.getNombre()).isEqualTo("Torneo Actualizado");
        assertThat(result.getCupoMaximo()).isEqualTo(32);
        assertThat(result.getModalidad()).isEqualTo("1v1");

        verify(torneoRepository, times(1)).findById(id);
        verify(torneoRepository, times(1)).save(torneoPrueba);
    }

    @Test
    @DisplayName("Debe lanzar excepción al actualizar torneo inexistente")
    public void shouldNotUpdateTorneoWhenNotExists() {
        Long id = 999L;
        when(torneoRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> torneoService.updateById(id, torneoDTO))
                .isInstanceOf(TorneoException.class)
                .hasMessage("Torneo no encontrado");

        verify(torneoRepository, times(1)).findById(id);
        verify(torneoRepository, never()).save(any(Torneo.class));
    }

    @Test
    @DisplayName("Debe eliminar torneo existente")
    public void shouldDeleteTorneoById() {
        Long id = 1L;
        when(torneoRepository.existsById(id)).thenReturn(true);

        torneoService.deleteById(id);

        verify(torneoRepository, times(1)).existsById(id);
        verify(torneoRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Debe lanzar excepción al eliminar torneo inexistente")
    public void shouldNotDeleteTorneoWhenNotExists() {
        Long id = 999L;
        when(torneoRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> torneoService.deleteById(id))
                .isInstanceOf(TorneoException.class)
                .hasMessage("Torneo no encontrado");

        verify(torneoRepository, times(1)).existsById(id);
        verify(torneoRepository, never()).deleteById(id);
    }
}