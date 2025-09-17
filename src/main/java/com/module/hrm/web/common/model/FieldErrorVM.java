package com.module.hrm.web.common.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FieldErrorVM implements Serializable {

    private final String field;

    private final String messageCode;

    private final String messageDetail;
}
