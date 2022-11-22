package com.gabriel.algafood.api.v1.openapi.model;

import com.gabriel.algafood.api.v1.model.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
@ApiModel("PermissoesModel")
public class PermissoesModelOpenApi {

    private PermissoesEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("PermissoesEmbeddedModel")
    private class PermissoesEmbeddedModelOpenApi {
        private List<PermissaoModel> permissoes;
    }
}
