package com.gabriel.algafood.core.validation.service;

import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.repository.EstadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EstadoService {

    private EstadoRepository repository;

    @Transactional(readOnly = true)
    public List<Estado> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Estado buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    @Transactional
    public void remover(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new EstadoNaoEncontradoException(ex.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Estado, id: #"+id+" está em uso e não pode ser removido.");
        }
    }
}
