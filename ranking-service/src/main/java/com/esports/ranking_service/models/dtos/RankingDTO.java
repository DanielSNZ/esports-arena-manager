package com.esports.ranking_service.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RankingDTO {

    @NotNull(message = "El equipoId no puede ser nulo")
    private Long equipoId;

    @NotNull(message = "El torneoId no puede ser nulo")
    private Long torneoId;

    @NotNull(message = "Los puntos no pueden ser nulos")
    private Integer puntos;

    @NotNull(message = "Las victorias no pueden ser nulas")
    private Integer victorias;

    @NotNull(message = "Las derrotas no pueden ser nulas")
    private Integer derrotas;

    @NotNull(message = "La posición no puede ser nula")
    private Integer posicion;
}
