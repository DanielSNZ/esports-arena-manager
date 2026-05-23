package com.esports.match_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartidaDTO {

    @NotNull(message = "El torneoId no puede ser nulo")
    private Long torneoId;

    @NotNull(message = "El equipo local no puede ser nulo")
    private Long equipoLocalId;

    @NotNull(message = "El equipo visitante no puede ser nulo")
    private Long equipoVisitanteId;

    @NotNull(message = "La fecha de partida no puede ser nula")
    private LocalDateTime fechaPartida;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    private Long ganadorId;
}