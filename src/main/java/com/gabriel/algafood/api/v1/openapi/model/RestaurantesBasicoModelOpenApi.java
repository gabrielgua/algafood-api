package com.gabriel.algafood.api.v1.openapi.model;

import com.gabriel.algafood.api.v1.model.RestauranteBasicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("RestaurantesBasicoModel")
public class RestaurantesBasicoModelOpenApi {

    private RestaurantesBasicoEmbeddedModelOpenApi _embedded;
    private Links _links;

    @Data
    @ApiModel("RestaurantesBasicoEmbeddedModel")
    private class RestaurantesBasicoEmbeddedModelOpenApi {
        private List<RestauranteBasicoModel> restaurantes;
    }
}
