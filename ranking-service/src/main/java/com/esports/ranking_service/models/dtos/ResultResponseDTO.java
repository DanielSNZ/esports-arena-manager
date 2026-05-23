package com.esports.ranking_service.models.dtos;

import lombok.Data;

@Data
public class ResultResponseDTO {

    private Long resultadoId;
    private Long partidaId;
    private Long ganadorId;
    private Integer puntajeA;
    private Integer puntajeB;
    private String estadoValidacion;
    private String fechaRegistro;
}