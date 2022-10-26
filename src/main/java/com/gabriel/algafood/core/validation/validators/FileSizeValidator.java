package com.gabriel.algafood.core.validation.validators;

import com.gabriel.algafood.core.validation.annotations.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize;


    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile arquivo, ConstraintValidatorContext constraintValidatorContext) {
//        if (arquivo != null) {
//            if (arquivo.getSize() <= maxSize.toBytes()) {
//                return true;
//            }
//        }
//
//        return false;

        return arquivo == null || arquivo.getSize() <= maxSize.toBytes();
    }
}
