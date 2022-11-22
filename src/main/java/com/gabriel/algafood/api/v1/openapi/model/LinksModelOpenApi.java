package com.gabriel.algafood.api.v1.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinksModelOpenApi {

    private LinkModel rel;

    @Getter
    @Setter
    @ApiModel("Link")
    private class LinkModel {

        private String href;
        private boolean templated;
    }

}
