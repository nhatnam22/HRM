package com.module.hrm.web.module.heirarchical.enumation;

import com.module.hrm.web.common.enumeration.BaseEnum;
import com.module.hrm.web.common.enumeration.MessageParams;
import lombok.Getter;

public enum DepartmentEnum implements BaseEnum {
    BUSINESS("00", "department.business", "Business"),
    ACCOUNTING("01", "department.accounting", "Accounting"),
    TECHNOLOGY("02", "department.technology", "Technology"),
    INVENTORY("30", "department.inventory", "inventory"),
    SALES("40", "department.sales", "Sales");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    DepartmentEnum(String code, String messageId, String defaultMsg) {
        this.code = code;
        this.messageId = messageId;
        this.defaultMsg = defaultMsg;
    }

    public String getValue() {
        return this.code;
    }

    @Override
    public MessageParams getMessageParam() {
        return MessageParams.valueOf(this.messageId);
    }
}
