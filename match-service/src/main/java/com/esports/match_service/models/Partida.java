package com.esports.match_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partida_id")
    private Long partidaId;

    @NotNull(message = "El torneoId no puede ser nulo")
    @Column(nullable = false)
    private Long torneoId;

    @NotNull(message = "El equipo local no puede ser nulo")
    @Column(nullable = false)
    private Long equipoLocalId;

    @NotNull(message = "El equipo visitante no puede ser nulo")
    @Column(nullable = false)
    private Long equipoVisitanteId;

    @NotNull(message = "La fecha de partida no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaPartida;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;

    private Long ganadorId;
}
