package com.esports.registration_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscripciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inscripcion_id")
    private Long inscripcionId;

    @NotNull(message = "El torneoId no puede ser nulo")
    @Column(nullable = false)
    private Long torneoId;

    @NotNull(message = "El equipoId no puede ser nulo")
    @Column(nullable = false)
    private Long equipoId;

    @NotNull(message = "La fecha de inscripción no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaInscripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;
}