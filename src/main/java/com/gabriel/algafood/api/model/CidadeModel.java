package com.gabriel.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

//@ApiModel(value = "Cidade", description = "Representa uma cidade")
@Getter
@Setter
public class CidadeModel extends RepresentationModel<CidadeModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlândia")
    private String nome;

    private EstadoModel estado;
}
