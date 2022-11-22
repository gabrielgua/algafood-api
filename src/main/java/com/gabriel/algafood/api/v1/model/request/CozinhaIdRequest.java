package com.gabriel.algafood.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CozinhaIdRequest {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;
}
