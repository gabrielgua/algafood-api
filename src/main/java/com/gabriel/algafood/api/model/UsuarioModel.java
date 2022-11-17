package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.OffsetDateTime;

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
