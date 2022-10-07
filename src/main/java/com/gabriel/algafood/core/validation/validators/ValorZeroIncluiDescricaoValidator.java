package com.gabriel.algafood.core.validation.validators;

import com.gabriel.algafood.core.validation.annotations.ValorZeroIncluiDescricao;
import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {

    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;
        try {
            var valor = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valorField)
                    .getReadMethod()
                    .invoke(o);

            String descricao = (String) BeanUtils.getPropertyDescriptor(o.getClass(), descricaoField)
                    .getReadMethod()
                    .invoke(o);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }
        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return valido;
    }
}
