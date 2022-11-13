package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.FormaPagamentoAssembler;
import com.gabriel.algafood.api.model.FormaPagamentoModel;
import com.gabriel.algafood.api.model.request.FormaPagamentoRequest;
import com.gabriel.algafood.domain.repository.FormaPagamentoRepository;
import com.gabriel.algafood.domain.service.FormaPagamentoService;
import com.gabriel.algafood.domain.model.FormaPagamento;
import lombok.AllArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("formas-pagamento")
public class FormaPagamentoController {

    private FormaPagamentoService service;
    private FormaPagamentoAssembler assembler;
    private FormaPagamentoRepository repository;


    //Implementação de Deep ETags
    @GetMapping
    public ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request) {
        ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());

        var eTag = "0";
        var dataUltimaAtualizacao = repository.getDataUltimaAtualizacao();
        if (dataUltimaAtualizacao != null) {
            eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
        }

        if (request.checkNotModified(eTag)) {
            return null;
        }

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

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long id) {
        var formaPagamentoModel = assembler.toModel(service.buscarPorId(id));
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formaPagamentoModel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel salvar(@RequestBody @Valid FormaPagamentoRequest request) {
        FormaPagamento formaPagamento = assembler.toEntity(request);
        return assembler.toModel(service.salvar(formaPagamento));
    }

    @PutMapping("/{id}")
    public FormaPagamentoModel editar(@PathVariable Long id,@RequestBody @Valid FormaPagamentoRequest request) {
        var formaPagamento = service.buscarPorId(id);
        assembler.copyToEntity(request, formaPagamento);
        return assembler.toModel(service.salvar(formaPagamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
