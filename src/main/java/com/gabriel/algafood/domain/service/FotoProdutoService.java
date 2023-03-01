package com.gabriel.algafood.domain.service;

import com.gabriel.algafood.core.storage.StorageProperties;
import com.gabriel.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.gabriel.algafood.domain.model.FotoProduto;
import com.gabriel.algafood.domain.repository.ProdutoRepository;
import com.gabriel.algafood.domain.service.FotoStorageService.NovaFoto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class FotoProdutoService {

    private ProdutoRepository produtoRepository;
    private FotoStorageService fotoStorageService;
    private StorageProperties properties;

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        var restauranteId = foto.getRestauranteId();
        var produtoId = foto.getProduto().getId();
        var fotoExistente = produtoRepository.findFotoById(restauranteId, produtoId);
        var nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;


        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
        }

        foto.setNomeArquivo(nomeNovoArquivo);
        foto.setCaminho(gerarUrlArquivo(nomeNovoArquivo));
        foto = produtoRepository.save(foto);
        produtoRepository.flush();


        NovaFoto novaFoto = NovaFoto.builder()
                .nomeArquivo(nomeNovoArquivo)
                .contentType(foto.getContentType())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);

        return foto;
    }


    public FotoProduto buscarPorId(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId).orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    @Transactional
    public void remover(Long restauranteId, Long produtoId) {
        var fotoProduto = buscarPorId(restauranteId, produtoId);

        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }

    private String gerarUrlArquivo(String nomeArquivo) {

        return properties.getTipo().equals(StorageProperties.TipoStorage.S3)
                ? fotoStorageService.recuperar(nomeArquivo).getUrl()
                : fotoStorageService.getArquivoPath(nomeArquivo);
    }

}
