package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.FormaPagamentoAssembler;
import com.gabriel.algafood.api.assembler.RestauranteAssembler;
import com.gabriel.algafood.api.model.FormaPagamentoModel;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private RestauranteService service;

    private FormaPagamentoAssembler assembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarPorId(restauranteId);
        return assembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincular(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        service.desvincularFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincular(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        service.vincularFormaPagamento(restauranteId, formaPagamentoId);
    }
}
