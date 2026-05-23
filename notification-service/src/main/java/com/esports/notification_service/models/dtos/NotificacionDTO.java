package com.esports.notification_service.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacionDTO {

    @NotNull(message = "El usuarioId no puede ser nulo")
    private Long usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    @NotBlank(message = "El tipo no puede estar vacío")
    private String tipo;

    @NotNull(message = "La fecha de envío no puede ser nula")
    private LocalDateTime fechaEnvio;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;
}