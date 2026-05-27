package com.esports.registration_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InscripcionDTO {

    @NotNull(message = "El torneoId no puede ser nulo")
    private Long torneoId;

    @NotNull(message = "El equipoId no puede ser nulo")
    private Long equipoId;

    @NotNull(message = "La fecha de inscripción no puede ser nula")
    private LocalDateTime fechaInscripcion;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}