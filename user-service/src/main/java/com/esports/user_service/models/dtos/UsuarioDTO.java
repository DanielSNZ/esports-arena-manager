package com.esports.user_service.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El nickname no puede estar vacío")
    private String nickname;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @NotBlank(message = "El rol no puede estar vacío")
    private String rol;

    @NotBlank(message = "El estado no puede estar vacío")
    private String estado;

    private String fechaRegistro;
}