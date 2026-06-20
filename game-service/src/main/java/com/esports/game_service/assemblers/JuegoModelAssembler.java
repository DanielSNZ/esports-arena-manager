package com.esports.game_service.assemblers;

import com.esports.game_service.controllers.JuegoControllerV2;
import com.esports.game_service.models.Juego;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class JuegoModelAssembler implements RepresentationModelAssembler<Juego, EntityModel<Juego>> {

    @Override
    public EntityModel<Juego> toModel(Juego juego) {
        return EntityModel.of(
                juego,
                linkTo(methodOn(JuegoControllerV2.class).findById(juego.getJuegoId())).withSelfRel(),
                linkTo(methodOn(JuegoControllerV2.class).findAll()).withRel("juegos")
        );
    }
}