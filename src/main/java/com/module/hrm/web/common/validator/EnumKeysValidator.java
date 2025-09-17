package com.module.hrm.web.common.validator;

import com.module.hrm.web.common.enumeration.BaseEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

public class EnumKeysValidator implements ConstraintValidator<EnumKeys, CharSequence> {

    private List<String> acceptedValues;

    @Override
    public void initialize(EnumKeys annotation) {
        Class<?> constantClass = annotation.value();

        if (constantClass.isEnum()) {
            BaseEnum[] values = (BaseEnum[]) constantClass.getEnumConstants();
            acceptedValues = Stream.of(values).map(v -> v.getValue().toUpperCase()).collect(Collectors.toList());
        } else {
            acceptedValues = new ArrayList<String>();
        }
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            return acceptedValues.contains(value.toString().toUpperCase());
        }

        return true;
    }
}
