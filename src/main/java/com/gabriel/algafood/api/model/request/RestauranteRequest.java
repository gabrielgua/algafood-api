package com.gabriel.algafood.api.model.request;

import com.gabriel.algafood.core.validation.annotations.ValorZeroIncluiDescricao;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
@ValorZeroIncluiDescricao(
        valorField = "taxaFrete",
        descricaoField = "nome",
        descricaoObrigatoria="Frete Grátis"
)
public class RestauranteRequest {

    @ApiModelProperty(example = "1")
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "12.00")
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdRequest cozinha;

    @Valid
    @NotNull
    private EnderecoRequest endereco;
}
