package com.gabriel.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Getter
@Setter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @ApiModelProperty(example = "6")
    private Long id;

    @ApiModelProperty(example = "Débora Mendonça")
    private String nome;

    @ApiModelProperty(example = "debora.mendonca@algafood.com.br")
    private String email;
}
