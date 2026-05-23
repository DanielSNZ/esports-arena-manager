package com.esports.match_service.models.dtos;

import lombok.Data;

@Data
public class TeamResponseDTO {

    private Long equipoId;
    private String nombre;
    private Long capitanId;
    private Long juegoPrincipalId;
    private String estado;
}