package com.gabriel.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class UsuarioModel {

    private Long id;
    private String nome;
    private String email;
    private OffsetDateTime dataCadastro;
}
