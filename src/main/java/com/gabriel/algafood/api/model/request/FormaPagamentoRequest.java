package com.gabriel.algafood.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoRequest {

    @ApiModelProperty(example = "Pix", required = true)
    @NotBlank
    private String descricao;
}
