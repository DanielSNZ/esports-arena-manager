package com.esports.notification_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Long notificacionId;

    @NotNull(message = "El usuarioId no puede ser nulo")
    @Column(nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(nullable = false)
    private String mensaje;

    @NotBlank(message = "El tipo no puede estar vacío")
    @Column(nullable = false)
    private String tipo;

    @NotNull(message = "La fecha de envío no puede ser nula")
    @Column(nullable = false)
    private LocalDateTime fechaEnvio;

    @NotBlank(message = "El estado no puede estar vacío")
    @Column(nullable = false)
    private String estado;
}