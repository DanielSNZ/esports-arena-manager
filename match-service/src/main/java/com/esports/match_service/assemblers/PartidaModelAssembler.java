package com.esports.match_service.assemblers;

import com.esports.match_service.controllers.PartidaControllerV2;
import com.esports.match_service.models.Partida;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PartidaModelAssembler implements RepresentationModelAssembler<Partida, EntityModel<Partida>> {

    @Override
    public EntityModel<Partida> toModel(Partida partida) {
        return EntityModel.of(
                partida,
                linkTo(methodOn(PartidaControllerV2.class).findById(partida.getPartidaId())).withSelfRel(),
                linkTo(methodOn(PartidaControllerV2.class).findAll()).withRel("partidas")
        );
    }
}