package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.FormaPagamentoAssembler;
import com.gabriel.algafood.api.v1.model.FormaPagamentoModel;
import com.gabriel.algafood.api.v1.model.request.FormaPagamentoRequest;
import com.gabriel.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.gabriel.algafood.domain.model.FormaPagamento;
import com.gabriel.algafood.domain.repository.FormaPagamentoRepository;
import com.gabriel.algafood.domain.service.FormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/formas-pagamento")
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private FormaPagamentoService service;
    private FormaPagamentoAssembler assembler;
    private FormaPagamentoRepository repository;


    //Implementação de Deep ETags
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
        var eTag = "0";
        var data = repository.getUltimaDataAtualizacao();
        eTag = String.valueOf(data.toEpochSecond());

        if (request.checkNotModified(eTag)) return null;

        var formasPagamentoModel = assembler.toCollectionModel(service.listar());
        return ResponseEntity.ok()
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
//                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
//                .cacheControl(CacheControl.noCache()) <- Always stale, must verify
//                .cacheControl(CacheControl.noStore()) <- No cache
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .body(formasPagamentoModel);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long id, ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        var eTag = "0";
        var dataUltimaAtualizacao = repository.getUltimaDataAtualizacaoById(id);
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) return null;


        var formaPagamentoModel = assembler.toModel(service.buscarPorId(id));
        return ResponseEntity.ok()
                .eTag(eTag)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoModel);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoRequest request) {
        FormaPagamento formaPagamento = assembler.toEntity(request);
        return assembler.toModel(service.salvar(formaPagamento));
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FormaPagamentoModel editar(@PathVariable Long id,@RequestBody @Valid FormaPagamentoRequest request) {
        var formaPagamento = service.buscarPorId(id);
        assembler.copyToEntity(request, formaPagamento);
        return assembler.toModel(service.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        service.remover(id);
    }
}
