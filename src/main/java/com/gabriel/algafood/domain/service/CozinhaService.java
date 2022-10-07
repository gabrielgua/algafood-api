package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.repository.CozinhaRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CozinhaService {

    private CozinhaRepository repository;

    public List<Cozinha> listar() {
        return repository.findAll();
    }

    public Cozinha buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return repository.save(cozinha);
    }

    @Transactional
    public void remover(Long id) {
        try {
            buscarPorId(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cozinha, id: #"+id+" está em uso e não pode ser removida.");
        }
    }
}
