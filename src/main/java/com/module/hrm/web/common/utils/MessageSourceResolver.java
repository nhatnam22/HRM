package com.module.hrm.web.common.utils;

import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.enumeration.MessageParams;
import com.module.hrm.web.common.model.FieldErrorVM;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

@Component
@AllArgsConstructor
public class MessageSourceResolver {

    private final MessageSource messageSource;

    public List<FieldErrorVM> processAllErrors(BindingResult result) {
        List<FieldErrorVM> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f ->
                new FieldErrorVM(
                    f.getField(),
                    MessageKeys.valueOf(f.getCode()).getValue(),
                    resolveLocalizedMessage(MessageKeys.valueOf(f.getCode()), f.getArguments())
                )
            )
            .collect(Collectors.toList());

        return fieldErrors;
    }

    public List<FieldErrorVM> processValidationErrors(BindingResult result) {
        List<FieldErrorVM> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f ->
                new FieldErrorVM(
                    f.getField(),
                    MessageKeys.valueOf(f.getCode()).getValue(),
                    resolveLocalizedMessage(MessageKeys.valueOf(f.getCode()), f.getArguments())
                )
            )
            .collect(Collectors.toList());

        return fieldErrors;
    }

    public String resolveLocalizedMessage(MessageKeys code, Object... args) {
        try {
            Locale currentLocale = LocaleContextHolder.getLocale();

            // localized param
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof MessageParams) {
                        String fieldName = ((MessageParams) args[i]).getValue();
                        args[i] = this.messageSource.getMessage(fieldName, null, currentLocale);
                    } else if (args[i] instanceof DefaultMessageSourceResolvable) {
                        DefaultMessageSourceResolvable source = ((DefaultMessageSourceResolvable) args[i]);
                        String fieldName = StringUtils.hasText(source.getDefaultMessage()) ? source.getDefaultMessage() : source.getCode();

                        // remove sub class
                        if (fieldName.indexOf('.') > 0) {
                            String[] names = fieldName.split("\\.");
                            if (names.length > 0) {
                                fieldName = names[names.length - 1];
                            }
                        }

                        try {
                            args[i] = this.messageSource.getMessage(fieldName, null, currentLocale);
                        } catch (NoSuchMessageException e) {
                            args[i] = fieldName;
                        }
                    }
                }
            }

            return this.messageSource.getMessage(code.getValue(), args, currentLocale);
        } catch (NoSuchMessageException e) {
            throw new RuntimeException("resolveLocalizedMessage: key does not exist " + code.getValue());
        }
    }

    public String resolveLocalizedMessage(String key) {
        try {
            Locale currentLocale = LocaleContextHolder.getLocale();
            return this.messageSource.getMessage(key, null, currentLocale);
        } catch (NoSuchMessageException e) {
            throw new RuntimeException("resolveLocalizedMessage: key does not exist " + key);
        }
    }
}
