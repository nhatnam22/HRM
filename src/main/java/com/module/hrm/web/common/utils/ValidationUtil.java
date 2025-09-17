package com.module.hrm.web.common.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class ValidationUtil {

    private static final Validator javaxValidator = Validation.buildDefaultValidatorFactory().getValidator();
    private static final SpringValidatorAdapter validator = new SpringValidatorAdapter(javaxValidator);

    /**
     * Validate object
     */
    public static BindingResult validate(Object obj) {
        BindingResult result = new BeanPropertyBindingResult(obj, obj.getClass().getName());
        validator.validate(obj, result);

        return result;
    }

    private ValidationUtil() {}
}
