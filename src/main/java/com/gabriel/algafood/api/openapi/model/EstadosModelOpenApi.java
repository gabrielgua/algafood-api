package com.gabriel.algafood.api.openapi.model;

import com.gabriel.algafood.api.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Link;

import java.util.List;

@Getter
@Setter
@ApiModel("EstadosModel")
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Link _links;

    @Getter
    @Setter
    @ApiModel("EstadosEmbeddedModel")
    private class EstadosEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }
}
