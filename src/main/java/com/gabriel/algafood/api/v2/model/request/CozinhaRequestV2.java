package com.gabriel.algafood.api.v2.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CozinhaRequestV2 {

    @ApiModelProperty(example = "Alemã", required = true)
    @NotBlank
    private String nomeCozinha;
}
