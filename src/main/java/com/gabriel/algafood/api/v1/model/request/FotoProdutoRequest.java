package com.gabriel.algafood.api.v1.model.request;

import com.gabriel.algafood.core.validation.annotations.FileContentType;
import com.gabriel.algafood.core.validation.annotations.FileSize;
import io.swagger.annotations.ApiModelProperty;
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
    @FileSize(max = "50MB")
//    @FileContentType(allowed = {MediaType.ALL_VALUE})
    private MultipartFile arquivo;

    @ApiModelProperty(example = "Prime rib mal passado")
    @NotBlank
    private String descricao;
}
