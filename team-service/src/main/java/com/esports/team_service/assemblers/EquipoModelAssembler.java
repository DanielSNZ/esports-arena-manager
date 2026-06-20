package com.esports.team_service.assemblers;

import com.esports.team_service.controllers.EquipoControllerV2;
import com.esports.team_service.models.Equipo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EquipoModelAssembler implements RepresentationModelAssembler<Equipo, EntityModel<Equipo>> {

    @Override
    public EntityModel<Equipo> toModel(Equipo equipo) {
        return EntityModel.of(
                equipo,
                linkTo(methodOn(EquipoControllerV2.class).findById(equipo.getEquipoId())).withSelfRel(),
                linkTo(methodOn(EquipoControllerV2.class).findAll()).withRel("equipos")
        );
    }
}