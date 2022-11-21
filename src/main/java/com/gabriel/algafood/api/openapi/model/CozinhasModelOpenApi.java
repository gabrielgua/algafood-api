package com.gabriel.algafood.api.openapi.model;

import com.gabriel.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

    private CozinhasEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;

    @Getter
    @Setter
    private class CozinhasEmbeddedModelOpenApi {
        private List<CozinhaModel> cozinhas;
    }

}
