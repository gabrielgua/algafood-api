package com.gabriel.algafood.api.v1.openapi.model;

import com.gabriel.algafood.api.v1.model.ProdutoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("ProdutosModel")
public class ProdutosModelOpenApi {

    private ProdutosEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("ProdutosEmbeddedModel")
    private class ProdutosEmbeddedModelOpenApi {
        private List<ProdutoModel> produtos;
    }
}
