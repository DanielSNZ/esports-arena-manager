package com.esports.result_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResultadoDTO {

    @NotNull(message = "La partida no puede ser nula")
    private Long partidaId;

    @NotNull(message = "El ganador no puede ser nulo")
    private Long ganadorId;

    @NotNull(message = "El puntaje A no puede ser nulo")
    private Integer puntajeA;

    @NotNull(message = "El puntaje B no puede ser nulo")
    private Integer puntajeB;

    @NotBlank(message = "El estado de validación no puede estar vacío")
    private String estadoValidacion;

    @NotNull(message = "La fecha de registro no puede ser nula")
    private LocalDateTime fechaRegistro;
}