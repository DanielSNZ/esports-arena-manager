package com.esports.game_service.models.dtos;

import lombok.Data;

@Data
public class JuegoDTO {

    private String nombre;

    private String genero;

    private String modalidad;

    private Integer jugadoresPorEquipo;

    private String estado;
}
