package com.module.hrm.web.common.exception;

import com.module.hrm.web.common.enumeration.MessageKeys;
import com.module.hrm.web.common.enumeration.MessageParams;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ValidationGenericException {

    public ResourceNotFoundException(String defaultMsg, MessageParams entityName) {
        super(HttpStatus.NOT_FOUND, defaultMsg, MessageKeys.ERROR_NOT_FOUND, entityName);
    }

    public ResourceNotFoundException(String defaultMsg, MessageParams entityName, String entityCode) {
        super(HttpStatus.NOT_FOUND, defaultMsg, MessageKeys.ERROR_CODE_NOT_FOUND, entityName, entityCode);
    }
}
