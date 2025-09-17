package com.module.hrm.web.common.enumeration;

import lombok.Getter;

public enum BusinessType implements BaseEnum {
    PERSONAL("0", "businessType.personal", "Hộ Kinh Doanh Cá Thể"),
    COMPANY("1", "businessType.company", "Công Ty");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    BusinessType(String code, String messageId, String defaultMsg) {
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
