package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.PedidoAssembler;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.domain.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;
    private PedidoAssembler assembler;

    @GetMapping
    public List<PedidoModel> listar() {
        return assembler.toCollectionModel(service.listar());
    }

    @GetMapping("/{id}")
    public PedidoModel buscarPorId(@PathVariable Long id) {
        return assembler.toModel(service.buscarPorId(id));
    }
}
