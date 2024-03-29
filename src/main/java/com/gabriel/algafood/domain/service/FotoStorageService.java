package com.gabriel.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);
    void remover(String nomeArquivo);

    String getArquivoPath(String nomeArquivo);


    FotoRecuperada recuperar(String nomeArquivo);

    default void substituir(String nomeArquivoExistente, NovaFoto novaFoto) {
        armazenar(novaFoto);

        if (nomeArquivoExistente != null) {
            remover(nomeArquivoExistente);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    @Getter
    @Builder
    class NovaFoto {
        private String nomeArquivo;
        private String contentType;
        private InputStream inputStream;
    }

    @Getter
    @Builder
    class FotoRecuperada {
        private InputStream inputStream;
        private String url;

        public boolean temUrl() {
            return url != null;
        }

        public boolean temInputStream() {
            return inputStream != null;
        }
    }
}
