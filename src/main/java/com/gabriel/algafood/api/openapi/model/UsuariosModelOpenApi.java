package com.gabriel.algafood.api.openapi.model;

import com.gabriel.algafood.api.model.UsuarioModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("UsuariosModel")
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private Links _links;


    @Data
    @ApiModel("UsuarioEmbeddedModel")
    private class UsuariosEmbeddedModelOpenApi {
        private List<UsuarioModel> usuarios;
    }
}
