package com.esports.team_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MiembroEquipoDTO {

    @NotNull(message = "El usuarioId no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "El rol dentro del equipo no puede estar vacío")
    private String rolDentroEquipo;
}