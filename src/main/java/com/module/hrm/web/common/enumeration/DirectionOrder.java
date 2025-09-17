package com.module.hrm.web.common.enumeration;

import lombok.Getter;

public enum DirectionOrder implements BaseEnum {
    ASC("ASC", "directionOrder.asc", "Sắp Xếp Tăng"),
    DESC("DESC", "directionOrder.desc", "Sắp Xếp Giảm");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    DirectionOrder(String code, String messageId, String defaultMsg) {
        this.code = code;
        this.messageId = messageId;
        this.defaultMsg = defaultMsg;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public MessageParams getMessageParam() {
        return MessageParams.valueOf(this.messageId);
    }
}
