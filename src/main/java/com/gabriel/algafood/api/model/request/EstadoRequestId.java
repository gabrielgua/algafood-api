package com.gabriel.algafood.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoRequestId {

    @NotNull
    private Long id;
}
