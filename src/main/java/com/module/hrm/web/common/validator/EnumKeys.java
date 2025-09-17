package com.module.hrm.web.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EnumKeysValidator.class })
public @interface EnumKeys {
    Class<? extends Enum<?>> value();

    String message() default "EnumKeys";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] exclude() default {};
}
