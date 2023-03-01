package com.gabriel.algafood.infrastructure.storage;

import com.gabriel.algafood.core.storage.StorageProperties;
import com.gabriel.algafood.domain.exception.StorageException;
import com.gabriel.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;


public class LocalFotoStorageService implements FotoStorageService {


    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        Path arquivoPath = Path.of(getArquivoPath(novaFoto.getNomeArquivo()));

        try {
            FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
        } catch (Exception ex) {
            throw new StorageException("Não foi possível armazenar o arquivo", ex);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        Path arquivoPath = Path.of(getArquivoPath(nomeArquivo));

        try {
            Files.deleteIfExists(arquivoPath);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível remover o arquivo", ex);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            Path arquivoPath = Path.of(getArquivoPath(nomeArquivo));

            return FotoRecuperada.builder()
                    .inputStream(Files.newInputStream(arquivoPath))
                    .build();
        } catch (Exception ex) {
            throw new StorageException("Não foi possível recuperar o arquivo", ex);
        }

    }

    @Override
    public String getArquivoPath(String nomeArquivo) {
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo)).toString();
    }
}
