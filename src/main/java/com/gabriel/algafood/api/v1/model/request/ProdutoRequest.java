package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @ApiModelProperty(example = "Camarão tailandês")
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "16 camarões grandes ao molho picante")
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "110.00")
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "false")
    @NotNull
    private Boolean ativo;
}
