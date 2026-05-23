package com.esports.registration_service.models.dtos;

import lombok.Data;

@Data
public class TournamentResponseDTO {

    private Long torneoId;
    private String nombre;
    private Long juegoId;
    private String estado;
}