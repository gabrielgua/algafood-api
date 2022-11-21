package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.gabriel.algafood.domain.model.Permissao;
import com.gabriel.algafood.domain.repository.PermissaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissaoService {

    private PermissaoRepository repository;

    public List<Permissao> listar() {
        return repository.findAll();
    }

    public Permissao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow((() -> new PermissaoNaoEncontradaException(id)));
    }
}
