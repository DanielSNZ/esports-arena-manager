package com.esports.sanction_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SancionDTO {

    @NotNull(message = "El usuarioId no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "El motivo no puede estar vacío")
    private String motivo;

    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;

    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDateTime fechaFin;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}