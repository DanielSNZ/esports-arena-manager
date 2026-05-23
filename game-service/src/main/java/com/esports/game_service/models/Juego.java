package com.esports.game_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "juegos")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "juego_id")
    private Long juegoId;

    @Column(nullable = false)
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Column(nullable = false)
    @NotBlank(message = "El género no puede estar vacío")
    private String genero;

    @Column(nullable = false)
    @NotBlank(message = "La modalidad no puede estar vacía")
    private String modalidad;

    @Column(name = "jugadores_por_equipo", nullable = false)
    @NotNull(message = "Jugadores por equipo no puede ser nulo")
    private Integer jugadoresPorEquipo;

    @Column(nullable = false)
    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}