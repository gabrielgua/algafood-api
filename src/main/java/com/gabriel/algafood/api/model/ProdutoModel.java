package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Camarão tailandês")
    private String nome;

    @ApiModelProperty(example = "16 camarões grandes ao molho picante")
    private String descricao;

    @ApiModelProperty(example = "110.00")
    private BigDecimal preco;

    @ApiModelProperty(example = "true")
    private boolean ativo;
}
