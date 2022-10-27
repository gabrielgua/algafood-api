package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.assembler.FotoProdutoAssembler;
import com.gabriel.algafood.api.model.FotoProdutoModel;
import com.gabriel.algafood.api.model.request.FotoProdutoRequest;
import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.service.FotoProdutoService;
import com.gabriel.algafood.domain.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private FotoProdutoService fotoProdutoService;
    private ProdutoService produtoService;
    private FotoProdutoAssembler assembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(
            @PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoRequest fotoProdutoRequest) {

        var produto = produtoService.buscarPorId(restauranteId, produtoId);
        var foto = new FotoProduto();
        var arquivo = fotoProdutoRequest.getArquivo();

        foto.setProduto(produto);
        foto.setDescricao(fotoProdutoRequest.getDescricao());
        foto.setTamanho(arquivo.getSize());
        foto.setContentType(arquivo.getContentType());
        foto.setNomeArquivo(arquivo.getOriginalFilename());

        return assembler.toModel(fotoProdutoService.salvar(foto));
    }
}
