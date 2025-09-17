package com.module.hrm.web.common.exception;

import com.module.hrm.web.common.enumeration.MessageKeys;
import lombok.Getter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

@Getter
public class BadAuthenticationException extends InternalAuthenticationServiceException {

    private MessageKeys messageCode;
    private Object[] messageArgs;

    public BadAuthenticationException(String message, MessageKeys messageCode, Object... messageArgs) {
        super(message);
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
    }
}
