package com.esports.sanction_service.models.dtos;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long usuarioId;
    private String nombre;
    private String nickname;
    private String email;
    private String rol;
    private String estado;
}