package com.gabriel.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Problem {

    @ApiModelProperty(example = "400")
    private Integer status;

    @ApiModelProperty(example = "2022-11-13T05:38:00Z")
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
    private String type;

    @ApiModelProperty(example = "Dados Inválidos")
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos")
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estão inválidos")
    private String userMessage;

    @ApiModelProperty(value = "Objetos ou campos que geraram o erro")
    private List<Object> objects;


    @ApiModel("objetoProblema")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "nome")
        private String name;

        @ApiModelProperty(example = "Nome da cidade não pode ser nulo")
        private String userMessage;
    }

}
