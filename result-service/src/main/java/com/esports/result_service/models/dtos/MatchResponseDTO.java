package com.esports.result_service.models.dtos;

import lombok.Data;

@Data
public class MatchResponseDTO {

    private Long partidaId;
    private Long torneoId;
    private Long equipoLocalId;
    private Long equipoVisitanteId;
    private String fechaPartida;
    private String estado;
    private Long ganadorId;
}