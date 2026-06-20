package com.esports.result_service.assemblers;

import com.esports.result_service.controllers.ResultadoControllerV2;
import com.esports.result_service.models.Resultado;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ResultadoModelAssembler implements RepresentationModelAssembler<Resultado, EntityModel<Resultado>> {

    @Override
    public EntityModel<Resultado> toModel(Resultado resultado) {
        return EntityModel.of(
                resultado,
                linkTo(methodOn(ResultadoControllerV2.class).findById(resultado.getResultadoId())).withSelfRel(),
                linkTo(methodOn(ResultadoControllerV2.class).findAll()).withRel("resultados")
        );
    }
}