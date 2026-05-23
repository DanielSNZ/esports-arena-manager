package com.esports.tournament_service.models.dtos;

import lombok.Data;

@Data
public class GameResponseDTO {

    private Long juegoId;
    private String nombre;
    private String genero;
    private String modalidad;
    private Integer jugadoresPorEquipo;
    private String estado;
}