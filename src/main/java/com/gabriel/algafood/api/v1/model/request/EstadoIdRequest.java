package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoIdRequest {

    @ApiModelProperty(example = "2", required = true)
    @NotNull
    private Long id;
}
