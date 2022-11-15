package com.gabriel.algafood.domain.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {

    @ApiModelProperty(example = "2022-11-14")
    private Date data;

    @ApiModelProperty(example = "12")
    private Long totalVendas;

    @ApiModelProperty(example = "120.00")
    private BigDecimal totalFaturado;
}
