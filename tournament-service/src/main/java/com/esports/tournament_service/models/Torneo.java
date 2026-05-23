package com.esports.tournament_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "torneos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Torneo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "torneo_id")
    private Long torneoId;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "El juegoId no puede ser nulo")
    @Column(nullable = false)
    private Long juegoId;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Column(nullable = false)
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(nullable = false)
    private LocalDate fechaFin;

    @NotNull(message = "El cupo máximo no puede ser nulo")
    @Column(nullable = false)
    private Integer cupoMaximo;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "La modalidad no puede estar vacía")
    @Column(nullable = false)
    private String modalidad;
}