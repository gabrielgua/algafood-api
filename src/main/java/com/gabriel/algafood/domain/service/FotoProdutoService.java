package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.gabriel.algafood.domain.service.FotoStorageService.NovaFoto;

import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FotoProdutoService {

    private ProdutoRepository produtoRepository;
    private FotoStorageService fotoStorageService;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        var nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());

        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }
        foto = produtoRepository.save(foto);
        produtoRepository.flush();


        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(nomeNovoArquivo)
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.armazenar(novaFoto);

        return foto;
    }
}
