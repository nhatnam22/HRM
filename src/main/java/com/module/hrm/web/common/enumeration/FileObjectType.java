package com.module.hrm.web.common.enumeration;

import lombok.Getter;

public enum FileObjectType implements BaseEnum {
    SYNC_DATABASE("sync", "fileObjectType.sync", "Tập Tin Đồng Bộ"),

    LEAD("leads", "fileObjectType.lead", "Ảnh KH Tiềm Năng"),

    CUSTOMER("customers", "fileObjectType.customer", "Ảnh Khách Hàng"),

    AVATAR("customer", "fileObjectType.avatar", "Ảnh Khách Hàng"),

    PRODUCT("product", "fileObjectType.product", "Ảnh Sản Phẩm"),
    PROMOTION("promotion", "fileObjectType.promotion", "Ảnh CTKM"),
    LOYALTY("loyalty", "fileObjectType.loyalty", "Ảnh CTTB/TL"),
    NOTIFICATION("notification", "fileObjectType.notification", "Ảnh Tin Nhắn Thông Báo"),
    COACHING("coachings", "fileObjectType.coaching", "Ảnh Huấn Luyện"),
    USER("users", "fileObjectType.user", "Ảnh Người Dùng"),
    VANSALES("vanSales", "fileObjectType.vanSales", "Ảnh Vansales"),
    VANSALES_SETT("vanSalesSett", "fileObjectType.vanSalesSett", "Ảnh QT Vansales"),
    ORDER("order", "fileObjectType.order", "Ảnh Đơn Hàng"),
    LUCKY_DRAW("luckyDraw", "fileObjectType.luckyDraw", "Ảnh Vòng Quay May Mắn"),
    FIELDWORK("fieldwork", "fileObjectType.field", "Ảnh Làm Việc Thực Địa"),
    TIMEKEEPING("timekeeping", "fileObjectType.timekeeping", "Timekeeping"),
    PURCHASE("purchase", "fileObjectType.purchase", "Tài liệu đơn mua hàng"),
    EXPENSE("expense", "fileObjectType.expense", "Chi phí");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    FileObjectType(String code, String messageId, String defaultMsg) {
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
