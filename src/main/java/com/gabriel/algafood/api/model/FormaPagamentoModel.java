package com.gabriel.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormaPagamentoModel {

    private Long id;
    private String descricao;
}
