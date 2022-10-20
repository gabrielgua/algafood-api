package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.PedidoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {

    private PedidoRepository repository;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
