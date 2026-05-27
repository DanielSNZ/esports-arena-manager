package com.esports.team_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class EquipoDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotNull(message = "El capitán no puede ser nulo")
    private Long capitanId;

    @NotNull(message = "El juego principal no puede ser nulo")
    private Long juegoPrincipalId;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    private List<MiembroEquipoDTO> miembros;
}