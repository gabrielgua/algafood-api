package com.gabriel.algafood.api.controller;

import com.gabriel.algafood.api.model.request.FotoProdutoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(
            @PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoRequest fotoProdutoRequest) {

        var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProdutoRequest.getArquivo().getOriginalFilename();
        var arquivoFoto = Path.of("C:/Users/Gabriel/Desktop/catalogo", nomeArquivo);


        try {
            fotoProdutoRequest.getArquivo().transferTo(arquivoFoto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
