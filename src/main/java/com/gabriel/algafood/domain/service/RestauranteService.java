package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.gabriel.algafood.domain.model.Cozinha;
import com.gabriel.algafood.domain.model.Restaurante;
import com.gabriel.algafood.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class RestauranteService {

    private RestauranteRepository repository;
    private CozinhaService cozinhaService;

    @Transactional(readOnly = true)
    public List<Restaurante> listar() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Restaurante buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarPorId(cozinhaId);
        restaurante.setCozinha(cozinha);
        return repository.save(restaurante);
    }

    @Transactional
    public void remover(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException ex) {
            throw new RestauranteNaoEncontradoException(ex.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Restaurante id: #"+id+" está em uso e não pode ser removido.");
        }
    }

}
