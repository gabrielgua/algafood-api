package com.gabriel.algafood.api.v1.openapi.model;

import com.gabriel.algafood.api.v1.model.PedidoResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("PedidoResumoModel")
public class PedidosResumoModelOpenApi {

    private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PageModelOpenApi page;


    @Data
    @ApiModel("PedidosEmbeddedModel")
    private class PedidosResumoEmbeddedModelOpenApi {
        private List<PedidoResumoModel> pedidos;
    }
}
