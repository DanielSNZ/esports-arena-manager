package com.esports.team_service.models.dtos;

import lombok.Data;

@Data
public class UserResponseDTO {

    private Long usuarioId;
    private String nombre;
    private String email;
    private String nickname;
    private String estado;
}
