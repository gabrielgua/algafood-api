package com.gabriel.algafood.api.v1.controller;

import com.gabriel.algafood.api.v1.assembler.FotoProdutoAssembler;
import com.gabriel.algafood.api.v1.model.FotoProdutoModel;
import com.gabriel.algafood.api.v1.model.request.FotoProdutoRequest;
import com.gabriel.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.gabriel.algafood.core.security.CheckSecurity;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.service.FotoProdutoService;
import com.gabriel.algafood.domain.service.FotoStorageService;
import com.gabriel.algafood.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    private FotoProdutoService fotoProdutoService;
    private FotoStorageService fotoStorageService;
    private ProdutoService produtoService;
    private FotoProdutoAssembler assembler;

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var fotoProduto = fotoProdutoService.buscarPorId(restauranteId, produtoId);
        return assembler.toModel(fotoProduto);
    }

    @GetMapping(produces = ALL_VALUE)
    public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
        try {
            var fotoProduto = fotoProdutoService.buscarPorId(restauranteId, produtoId);

            var mediaTypeFoto = parseMediaType(fotoProduto.getContentType());
            List<MediaType> mediaTypesAceitas = parseMediaTypes(acceptHeader);

            verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return ResponseEntity
                        .status(HttpStatus.FOUND)
                        .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ResponseEntity.ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }
    @CheckSecurity.Restaurantes.PodeEditar
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
    public FotoProdutoModel atualizarFoto(
            @PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoRequest fotoProdutoRequest) throws IOException {

        var produto = produtoService.buscarPorId(restauranteId, produtoId);
        var foto = new FotoProduto();
        var arquivo = fotoProdutoRequest.getArquivo();

        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoRequest.getDescricao());
        foto.setTamanho(arquivo.getSize());
        foto.setContentType(arquivo.getContentType());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        return assembler.toModel(fotoProdutoService.salvar(foto, arquivo.getInputStream()));
    }

    @CheckSecurity.Restaurantes.PodeEditar
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        fotoProdutoService.remover(restauranteId, produtoId);
    }

    private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }
}
