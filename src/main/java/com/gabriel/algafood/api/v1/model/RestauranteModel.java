package com.gabriel.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteModel extends RepresentationModel<RestauranteModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String nome;

    @ApiModelProperty(example = "12.00")
    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

    @ApiModelProperty(example = "true")
    private Boolean ativo;

    @ApiModelProperty(example = "false")
    private Boolean aberto;
    private EnderecoModel endereco;

}
