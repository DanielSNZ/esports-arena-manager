package com.esports.sanction_service.assemblers;

import com.esports.sanction_service.controllers.SancionControllerV2;
import com.esports.sanction_service.models.Sancion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class SancionModelAssembler implements RepresentationModelAssembler<Sancion, EntityModel<Sancion>> {

    @Override
    public EntityModel<Sancion> toModel(Sancion sancion) {
        return EntityModel.of(
                sancion,
                linkTo(methodOn(SancionControllerV2.class).findById(sancion.getSancionId())).withSelfRel(),
                linkTo(methodOn(SancionControllerV2.class).findAll()).withRel("sanciones")
        );
    }
}