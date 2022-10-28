package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.FotoProdutoAssembler;
import com.gabriel.algafood.api.model.FotoProdutoModel;
import com.gabriel.algafood.api.model.request.FotoProdutoRequest;
import com.gabriel.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.service.FotoProdutoService;
import com.gabriel.algafood.domain.service.FotoStorageService;
import com.gabriel.algafood.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.*;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private FotoProdutoService fotoProdutoService;
    private FotoStorageService fotoStorageService;
    private ProdutoService produtoService;
    private FotoProdutoAssembler assembler;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        var fotoProduto = fotoProdutoService.buscarPorId(restauranteId, produtoId);
        return assembler.toModel(fotoProduto);
    }

    @GetMapping(produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE})
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        try {
            var fotoProduto = fotoProdutoService.buscarPorId(restauranteId, produtoId);
            InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return ResponseEntity.ok().contentType(IMAGE_JPEG).body(new InputStreamResource(inputStream));

        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
}
