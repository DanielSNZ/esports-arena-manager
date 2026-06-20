package com.esports.tournament_service.assemblers;

import com.esports.tournament_service.controllers.TorneoControllerV2;
import com.esports.tournament_service.models.Torneo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TorneoModelAssembler implements RepresentationModelAssembler<Torneo, EntityModel<Torneo>> {

    @Override
    public EntityModel<Torneo> toModel(Torneo torneo) {
        return EntityModel.of(
                torneo,
                linkTo(methodOn(TorneoControllerV2.class).findById(torneo.getTorneoId())).withSelfRel(),
                linkTo(methodOn(TorneoControllerV2.class).findAll()).withRel("torneos")
        );
    }
}