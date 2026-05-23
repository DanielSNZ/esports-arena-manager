package com.esports.result_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resultados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultado_id")
    private Long resultadoId;

    @NotNull(message = "La partida no puede ser nula")
    @Column(nullable = false)
    private Long partidaId;

    @NotNull(message = "El ganador no puede ser nulo")
    @Column(nullable = false)
    private Long ganadorId;

    @NotNull(message = "El puntaje A no puede ser nulo")
    @Column(nullable = false)
    private Integer puntajeA;

    @NotNull(message = "El puntaje B no puede ser nulo")
    @Column(nullable = false)
    private Integer puntajeB;

    @NotBlank(message = "El estado de validación no puede estar vacío")
    @Column(nullable = false)
    private String estadoValidacion;

    @NotNull(message = "La fecha de registro no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
}