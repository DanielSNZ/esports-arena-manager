package com.esports.sanction_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sanciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sancion_id")
    private Long sancionId;

    @NotNull(message = "El usuarioId no puede ser nulo")
    @Column(nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El motivo no puede estar vacío")
    @Column(nullable = false)
    private String motivo;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Column(nullable = false)
    private String tipo;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;
}