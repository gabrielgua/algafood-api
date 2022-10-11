package com.gabriel.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstadoRequest {

    @NotBlank
    private String nome;
}
