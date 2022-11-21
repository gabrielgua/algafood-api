package com.gabriel.algafood.api.openapi.model;

import com.gabriel.algafood.api.model.FormaPagamentoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.List;

@Data
@ApiModel("FormasPagamentoModel")
public class FormasPagamentoModelOpenApi {

    private FormasPagamentoEmbeddedModelOpenApi _embedded;
    private Link _links;

    @Data
    @ApiModel("FormasPagamentoEmbeddedModel")
    private class FormasPagamentoEmbeddedModelOpenApi {
        private List<FormaPagamentoModel> formasPagamento;
    }
}
