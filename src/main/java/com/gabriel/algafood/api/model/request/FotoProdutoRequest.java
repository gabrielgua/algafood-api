package com.gabriel.algafood.api.model.request;

import com.gabriel.algafood.core.validation.annotations.FileContentType;
import com.gabriel.algafood.core.validation.annotations.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "10MB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;
}
