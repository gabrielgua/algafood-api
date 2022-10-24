package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.exception.PedidoNaoEncontradoException;
import com.gabriel.algafood.domain.model.Pedido;

import com.gabriel.algafood.domain.model.Produto;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.repository.PedidoRepository;
import com.gabriel.algafood.domain.repository.filter.PedidoFilter;
import com.gabriel.algafood.infrastructure.spec.PedidoSpecs;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {


    private PedidoRepository repository;
    private RestauranteService restauranteService;
    private FormaPagamentoService formaPagamentoService;
    private ProdutoService produtoService;
    private UsuarioService usuarioService;
    private CidadeService cidadeService;

    public Page<Pedido> listar(PedidoFilter filter, Pageable pageable) {
        return repository.findAll(PedidoSpecs.usandoFiltro(filter), pageable);
    }

    public Pedido buscarPorId(String codigoPedido) {
        return repository.findByCodigo(codigoPedido).orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
    }

    @Transactional
    public Pedido salvar(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.definirFrete();
        pedido.calcularValorTotal();

        return repository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        var cidade = cidadeService.buscarPorId(pedido.getEnderecoEntrega().getCidade().getId());
        var usuario = usuarioService.buscarPorId(pedido.getCliente().getId());
        var restaurante = restauranteService.buscarPorId(pedido.getRestaurante().getId());
        var formaPagamento = formaPagamentoService.buscarPorId(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(usuario);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaDePagamento(formaPagamento)) {
            throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.", formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarPorId(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
