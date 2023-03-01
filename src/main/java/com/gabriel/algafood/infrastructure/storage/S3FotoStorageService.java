package com.gabriel.algafood.infrastructure.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.gabriel.algafood.core.storage.StorageProperties;
import com.gabriel.algafood.domain.exception.StorageException;
import com.gabriel.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.nio.file.Path;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            var caminhoArquivo = getArquivoPath(novaFoto.getNomeArquivo());
            var objectMetaData = new ObjectMetadata();
            objectMetaData.setContentType(novaFoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                        storageProperties.getS3().getBucket(),
                        caminhoArquivo,
                        novaFoto.getInputStream(),
                        objectMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível enviar o arquivo para Amazon S3", ex);
        }


    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            var caminhoArquivo = getArquivoPath(nomeArquivo);
            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo
            );

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception ex) {
            throw new StorageException("Não foi possível remover o arquivo da Amazon S3", ex);
        }
    }

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        var caminhoArquivo = getArquivoPath(nomeArquivo);
        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo);

        return FotoRecuperada.builder()
                .url(url.toString())
                .build();
    }

    public String getArquivoPath(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }
}
