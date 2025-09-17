package com.module.hrm.web.common.model;

import java.util.List;

public abstract class AbstractRecordDto {

    private int lineNumber;
    private String errorMessage;

    /**
     * The header of CSV
     */
    public abstract List<String> getHeaders();

    /**
     * The field names mapping to header
     */
    public abstract List<String> getFields();

    /**
     * The field is double
     */
    public abstract boolean isDouble(String fieldName);

    /**
     * Parse object to array
     */
    public abstract String[] toArray();

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
