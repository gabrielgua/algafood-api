package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.EntidadeEmUsoException;
import com.gabriel.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.gabriel.algafood.domain.model.*;
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
    private CidadeService cidadeService;
    private FormaPagamentoService formaPagamentoService;
    private UsuarioService usuarioService;

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
        Long cidadeId = restaurante.getEndereco().getCidade().getId();

        Cozinha cozinha = cozinhaService.buscarPorId(cozinhaId);
        Cidade cidade = cidadeService.buscarPorId(cidadeId);

        restaurante.getEndereco().setCidade(cidade);
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

    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.inativar();
    }

    @Transactional
    public void ativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }

    @Transactional
    public void inativar(List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public Restaurante ativarOuInativar(Long id) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.ativarOuInativar();
        return restaurante;
    }

    @Transactional
    public void desvincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);

        restaurante.removerFormaPagamento(formaPagamento);
    }

    @Transactional
    public void vincularFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);

        restaurante.adicionarFormaPagamento(formaPagamento);
    }

    @Transactional
    public void abrirRestaurante(Long restauranteId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        restaurante.abrir();
    }

    @Transactional
    public void fecharRestaurante(Long restauranteId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        restaurante.fechar();
    }

    @Transactional
    public void abrirOuFecharRestaurante(Long restauranteId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        restaurante.abrirOuFechar();
    }

    @Transactional
    public void vincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        restaurante.vincularResponsavel(usuario);
    }

    @Transactional
    public void desvincularResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarPorId(restauranteId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        restaurante.desvincularResponsavel(usuario);
    }


}
