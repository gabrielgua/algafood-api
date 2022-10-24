package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.PedidoAssembler;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.PedidoResumoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.repository.filter.PedidoFilter;
import com.gabriel.algafood.domain.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoService service;
    private PedidoAssembler assembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(@PageableDefault(size = 10) Pageable pageable, PedidoFilter filter) {
        Page<Pedido> pedidos = service.listar(filter, pageable);
        List<PedidoResumoModel> pedidosResumoModel = assembler.toCollectionResumoModel(pedidos.getContent());
        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidos.getTotalElements());

        return pedidosResumoModelPage;
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscarPorId(@PathVariable String codigoPedido) {
        return assembler.toModel(service.buscarPorId(codigoPedido));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoModel salvar(@RequestBody @Valid PedidoRequest request) {
        try {
            Pedido pedido = assembler.toEntity(request);

            //usuario autenticado implementar
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            return assembler.toModel(service.salvar(pedido));
        } catch (EntidadeNaoEncontradaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }

    //  filtrar consulta por campos com MappingJacksonValue
    //    @GetMapping
//        public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
//        var pedidos = service.listar();
//        var pedidosModel = assembler.toCollectionResumoModel(pedidos);
//
//        MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//
//        if (StringUtils.isNotBlank(campos)) {
//            filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//        }
//
//        pedidosWrapper.setFilters(filterProvider);
//        return pedidosWrapper;
//    }
}
