package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.GrupoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Grupo;
import com.gabriel.algafood.domain.repository.GrupoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class GrupoService {

    private GrupoRepository repository;

    public List<Grupo> listar() {
        return repository.findAll();
    }

    public Grupo buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return repository.save(grupo);
    }

    @Transactional
    public void remover(Long id) {
        Grupo grupo = buscarPorId(id);
        repository.delete(grupo);
    }
}
