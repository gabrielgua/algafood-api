package com.gabriel.algafood.api.v2.openapi.model;

import com.gabriel.algafood.api.v2.model.CidadeModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CidadesModel")
public class CidadesModelOpenApiV2 {

    private CidadeEmbeddeModelOpenApiV2 _embedded;
    private Links _links;

    @Data
    @ApiModel("CidadeEmbeddedModel")
    public class CidadeEmbeddeModelOpenApiV2 {
        private List<CidadeModelV2> cidades;
    }
}
