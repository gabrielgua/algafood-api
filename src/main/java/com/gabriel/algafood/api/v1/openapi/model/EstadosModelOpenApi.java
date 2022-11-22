package com.gabriel.algafood.api.v1.openapi.model;

import com.gabriel.algafood.api.v1.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("EstadosModel")
public class EstadosModelOpenApi {

    private EstadosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Getter
    @Setter
    @ApiModel("EstadosEmbeddedModel")
    private class EstadosEmbeddedModelOpenApi {
        private List<EstadoModel> estados;
    }
}
