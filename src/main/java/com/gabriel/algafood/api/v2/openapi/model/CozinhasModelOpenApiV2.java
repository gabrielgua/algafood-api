package com.gabriel.algafood.api.v2.openapi.model;

import com.gabriel.algafood.api.v1.openapi.model.PageModelOpenApi;
import com.gabriel.algafood.api.v2.model.CozinhaModelV2;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CozinhasModel")
public class CozinhasModelOpenApiV2 {

    private CozinhaEmbeddedModelOpenApiV2 _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Data
    @ApiModel("CozinhaEmbeddedModel")
    public class CozinhaEmbeddedModelOpenApiV2 {
        private List<CozinhaModelV2> cozinhas;
    }
}
