package com.gabriel.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoModel extends RepresentationModel<PedidoModel> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "308.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2022-11-13T22:06:32Z")
    private OffsetDateTime dataCriacao;
    @ApiModelProperty(example = "2022-11-13T22:06:32Z")
    private OffsetDateTime dataConfirmacao;
    @ApiModelProperty(example = "2022-11-13T22:06:32Z")
    private OffsetDateTime dataCancelamento;
    @ApiModelProperty(example = "2022-11-13T22:06:32Z")
    private OffsetDateTime dataEntrega;

    private FormaPagamentoModel formaPagamento;
    private RestauranteApenasNomeModel restaurante;
    private UsuarioModel cliente;
    private EnderecoModel enderecoEntrega;
    private List<ItemPedidoModel> itens;
}
