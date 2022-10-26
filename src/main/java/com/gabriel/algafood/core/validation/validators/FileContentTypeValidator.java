package com.gabriel.algafood.core.validation.validators;

import com.gabriel.algafood.core.validation.annotations.FileContentType;
import com.gabriel.algafood.core.validation.annotations.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile arquivo, ConstraintValidatorContext constraintValidatorContext) {
        return arquivo == null
                || allowedContentTypes.contains(arquivo.getContentType());

    }
}
