package com.gabriel.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.gabriel.algafood.api.model.view.RestauranteView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel {

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    @ApiModelProperty(example = "1")
    private Long id;

    @JsonView({RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    @ApiModelProperty(example = "true")
    private Boolean ativo;

    @ApiModelProperty(example = "false")
    private Boolean aberto;
    private EnderecoModel endereco;

}
