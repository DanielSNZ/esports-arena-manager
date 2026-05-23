package com.esports.ranking_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "rankings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long rankingId;

    @NotNull(message = "El equipoId no puede ser nulo")
    @Column(nullable = false)
    private Long equipoId;

    @NotNull(message = "El torneoId no puede ser nulo")
    @Column(nullable = false)
    private Long torneoId;

    @NotNull(message = "Los puntos no pueden ser nulos")
    @Column(nullable = false)
    private Integer puntos;

    @NotNull(message = "Las victorias no pueden ser nulas")
    @Column(nullable = false)
    private Integer victorias;

    @NotNull(message = "Las derrotas no pueden ser nulas")
    @Column(nullable = false)
    private Integer derrotas;

    @NotNull(message = "La posición no puede ser nula")
    @Column(nullable = false)
    private Integer posicion;
}