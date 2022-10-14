package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.gabriel.algafood.domain.model.FormaPagamento;
import com.gabriel.algafood.domain.repository.FormaPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private FormaPagamentoRepository repository;

    public List<FormaPagamento> listar() {
        return repository.findAll();
    }

    public FormaPagamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new FormaPagamentoNaoEncontradaException(id));
    }

    @Transactional
    public FormaPagamento salvar(FormaPagamento formaPagamento) {
        return repository.save(formaPagamento);
    }

    @Transactional
    public void remover(Long id) {
        try {
            var formaPagamento = buscarPorId(id);
            repository.delete(formaPagamento);
            repository.flush();
//        } catch (EmptyResultDataAccessException ex) {
//            throw new EntidadeNaoEncontradaException(ex.getMessage());
        } catch (DataIntegrityViolationException ex) {
            throw new EntidadeEmUsoException("Forma de pagamento id: #"+id+" está em uso e não pode ser removida.");
        }
    }
}
