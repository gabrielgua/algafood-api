package com.gabriel.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "Admin")
    private String nome;

    @ApiModelProperty(example = "Adminitrador do sistema")
    private String descricao;
}
