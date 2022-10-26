package com.gabriel.algafood.api.model.request;

import com.gabriel.algafood.core.validation.annotations.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "10MB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
