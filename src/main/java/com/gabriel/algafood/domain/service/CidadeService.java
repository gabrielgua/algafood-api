package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.CidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.model.Cidade;
import com.gabriel.algafood.domain.model.Estado;
import com.gabriel.algafood.domain.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CidadeService {

    private CidadeRepository repository;
    private EstadoService estadoService;

    public List<Cidade> listar() {
        return repository.findAll();
    }

    public Cidade buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarPorId(estadoId);
        cidade.setEstado(estado);
        return repository.save(cidade);
    }

    @Transactional
    public void remover(Long id) {
        try {
            buscarPorId(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cidade, id: #"+id+" está em uso e não pode ser removida.");
        }
    }
















}
