package com.module.hrm.web.common.exception;

import com.module.hrm.web.common.enumeration.MessageKeys;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

@Getter
public class ValidationGenericException extends ErrorResponseException {

    private final String defaultMsg;

    private final MessageKeys messageCode;
    private final Object[] messageArgs;

    public ValidationGenericException(HttpStatus status, String defaultMsg, MessageKeys messageCode, Object... messageArgs) {
        super(status, null, null);
        this.defaultMsg = defaultMsg;
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
    }

    public ValidationGenericException(String defaultMsg, MessageKeys messageCode, Object... messageArgs) {
        this(HttpStatus.BAD_REQUEST, defaultMsg, messageCode, messageArgs);
    }
}
