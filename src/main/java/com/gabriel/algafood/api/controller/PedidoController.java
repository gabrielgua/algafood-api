package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.PedidoAssembler;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.PedidoResumoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.exception.*;
import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;
    private PedidoAssembler assembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        return assembler.toCollectionResumoModel(service.listar());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscarPorId(@PathVariable String codigoPedido) {
        return assembler.toModel(service.buscarPorId(codigoPedido));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel salvar(@RequestBody @Valid PedidoRequest request) {
        try {
            Pedido pedido = assembler.toEntity(request);

            //usuario autenticado implementar
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            return assembler.toModel(service.salvar(pedido));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}
