package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.ApiLinks;
import com.gabriel.algafood.api.v1.assembler.FormaPagamentoAssembler;
import com.gabriel.algafood.api.v1.model.FormaPagamentoModel;
import com.gabriel.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

    private RestauranteService service;

    private FormaPagamentoAssembler assembler;
    private ApiLinks links;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = service.buscarPorId(restauranteId);
        var formasPagamentoModel = assembler.toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(links.linkToRestauranteFormasPagamento(restauranteId))
                .add(links.linkToRestauranteVincularFormaPagamento(restauranteId, "vincular"));

        formasPagamentoModel.getContent().forEach(formaPagamentoModel ->
                formaPagamentoModel.add(links.linkToRestauranteDesvincularFormaPagamento(restauranteId, formaPagamentoModel.getId(), "desvincular")));



        return formasPagamentoModel;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desvincular(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        service.desvincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> vincular(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        service.vincularFormaPagamento(restauranteId, formaPagamentoId);
        return ResponseEntity.noContent().build();
    }
}
