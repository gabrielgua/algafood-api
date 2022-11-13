package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.PedidoAssembler;
import com.gabriel.algafood.api.model.PedidoModel;
import com.gabriel.algafood.api.model.PedidoResumoModel;
import com.gabriel.algafood.api.model.request.PedidoRequest;
import com.gabriel.algafood.api.openapi.controller.PedidoControllerModelOpenApi;
import com.gabriel.algafood.core.data.PageableTranslator;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.exception.NegocioException;
import com.gabriel.algafood.domain.model.Pedido;
import com.gabriel.algafood.domain.model.Usuario;
import com.gabriel.algafood.domain.filter.PedidoFilter;
import com.gabriel.algafood.domain.service.PedidoService;
import com.google.common.collect.ImmutableMap;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerModelOpenApi {

    private PedidoService service;
    private PedidoAssembler assembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<PedidoResumoModel> pesquisar(@PageableDefault(size = 10) Pageable pageable, PedidoFilter filter) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidos = service.listar(filter, pageable);
        List<PedidoResumoModel> pedidosResumoModel = assembler.toCollectionResumoModel(pedidos.getContent());
        Page<PedidoResumoModel> pedidosResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidos.getTotalElements());

        return pedidosResumoModelPage;
    }


    @GetMapping(path = "/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PedidoModel buscarPorId(@PathVariable String codigoPedido) {
        return assembler.toModel(service.buscarPorId(codigoPedido));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
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

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "restaurante.nome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"
        );

        return PageableTranslator.translate(apiPageable, mapeamento);
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
