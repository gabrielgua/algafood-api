package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FotoProdutoModel {

    @ApiModelProperty(example = "prime_rib.jpeg")
    private String nomeArquivo;

    @ApiModelProperty(example = "Prime Rib mal passado")
    private String descricao;

    @ApiModelProperty(example = "image/jpeg")
    private String contentType;

    @ApiModelProperty(example = "114170")
    private Long tamanho;
}
