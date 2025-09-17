package com.module.hrm.web.common.enumeration;

import lombok.Getter;

public enum UserType implements BaseEnum {
    SYSTEM("00", "userType.system", "System"),

    ADMIN("10", "userType.admin", "Admin"),
    DISTRIBUTOR_ASSIGNED("20", "userType.distributorAssigned", "Distributor Assigned"),

    DISTRIBUTOR("30", "userType.distributor", "Distributor"),

    SALES_MANAGER("40", "userType.sales", "Sales Manager"),
    SALESMAN("50", "userType.sales", "Salesman"),

    NONE("99", "userType.none", "None");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    UserType(String code, String messageId, String defaultMsg) {
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
