package com.esports.team_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "miembros_equipo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MiembroEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "miembro_id")
    private Long miembroId;

    @NotNull(message = "El usuarioId no puede ser nulo")
    @Column(nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El rol dentro del equipo no puede estar vacío")
    @Column(nullable = false)
    private String rolDentroEquipo;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;
}