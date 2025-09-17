package com.module.hrm.web.common.enumeration;

import lombok.Getter;

public enum CommonCode implements BaseEnum {
    APPLICATION_CONSTANT("APPLICATION_CONSTANT", "applicationConstant", "Hằng Số Hệ Thống"),

    BUSINESS_TYPE("BUSINESS_TYPE", "businessType", "Loại Hình Kinh Doanh"),

    // For Distributor
    DISTRIBUTION_CHANNEL("DISTRIBUTION_CHANNEL", "distributionChannel", "Kênh Nhà Phân Phối"),
    LEGAL_FORM("LEGAL_FORM", "legalForm", "Loại Hình Kinh Doanh"),
    DISTRIBUTOR_TRUCK_TYPE("DISTRIBUTOR_TRUCK_TYPE", "distributorTruckType", "Loại Xe NPP"),

    // For Customer
    CUSTOMER_SIZE_TYPE("CUSTOMER_SIZE_TYPE", "customerSizeType", "Độ Lớn Ngành Mì"),
    CUSTOMER_SIZE_TYPE2("CUSTOMER_SIZE_TYPE2", "customerSizeType2", "Độ Lớn Ngành Mì UNB"),
    DISPLAY_CHANNEL("DISPLAY_CHANNEL", "displayChannel", "Đặc Điểm Trưng Bày"),

    CHANNEL_CATEGORY("CHANNEL_CATEGORY", "channelCategory", "Kênh Khách Hàng"),
    TRADE_CHANNEL("TRADE_CHANNEL", "tradeChannel", "Nhóm Khách Hàng"),
    SUB_TRADE_CHANNEL("SUB_TRADE_CHANNEL", "subTradeChannel", "Loại Khách Hàng"),

    OUTLET_CARE_TYPE("OUTLET_CARE_TYPE", "outletCareType", "Viếng Thăm Khách Hàng"),
    VISIT_REMOTE_TYPE("VISIT_REMOTE_TYPE", "visitRemoteType", "Viếng Thăm Từ Xa"),
    VISIT_TIME_UNDER("VISIT_TIME_UNDER", "visitTimeUnder", "Thời Gian Viếng Thăm"),
    NO_SELL_REASON_TYPE("NO_SELL_REASON_TYPE", "noSellReasonType", "Lý Do Không Mua Hàng"),

    // For inventory
    INVENTORY_ENTRY_TYPE("INVENTORY_ENTRY_TYPE", "inventoryEntryType", "Loại Điều Chỉnh"),
    INVENTORY_REASON_TYPE("INVENTORY_REASON_TYPE", "inventoryReasonType", "Lý Do Điều Chỉnh"),

    // For survey
    QUESTION_TYPE("QUESTION_TYPE", "questionType", "Loại Câu Hỏi"),
    OTHER_OPTION_TYPE("OTHER_OPTION_TYPE", "otherOptionType", "Loại Câu Trả Lời"),
    SURVEY_SET_TYPE("SURVEY_SET_TYPE", "surveySetType", "Loại SurveySet"),

    SALES_TEAM("SALES_TEAM", "salesTeam", "Đội Bán Hàng"),

    DISTRIBUTOR_VIEW("DISTRIBUTOR_VIEW", "distributorView", "Quyền Xem NPP"),

    // For product
    MATERIAL_TYPE("MATERIAL_TYPE", "materialType", "Loại Vật Liệu"),
    MATERIAL_GROUP("MATERIAL_GROUP", "materialGroup", "Nhóm Vật Liệu"),
    PRODUCT_UOM("PRODUCT_UOM", "productUom", "Đơn Vị Tính"),

    PRODUCT_GROUP_L1("PRODUCT_GROUP_L1", "productGroup", "Nhóm Sản Phẩm"),
    PRODUCT_CATEGORY_L2("PRODUCT_CATEGORY_L2", "productCategory", "Danh Mục Sản Phẩm"),
    PRODUCT_BRAND_L3("PRODUCT_BRAND_L3", "productBrand", "Nhãn Sản Phẩm"),
    PRODUCT_LINE_L4("PRODUCT_LINE_L4", "productLine", "Dòng Sản Phẩm"),
    PRODUCT_FLAVOR_L5("PRODUCT_FLAVOR_L5", "productFlavor", "Vị Sản Phẩm"),
    PACK_SIZE_L6("PACK_SIZE_L6", "packSize", "Kích Cỡ Gói"),
    PACK_TYPE_L7("PACK_TYPE_L7", "packType", "Loại Gói"),
    UNIT_PER_CASE_L8("UNIT_PER_CASE_L8", "unitPerCase", "Đơn Vị Thùng"),
    PACK_GROUP_L9("PACK_GROUP_L9", "packGroup", "Nhóm Gói"),

    CATALOG_TYPE("CATALOG_TYPE", "catalogType", "Danh Mục Sản Phẩm"),

    PROGRAM_TYPE("PROGRAM_TYPE", "programType", "Loại Chương Trình"),
    DETAIL_GROUP("DETAIL_GROUP", "detailGroup", "Loại Chi Tiết KM"),
    PROMOTION_TYPE("PROMOTION_TYPE", "promotionType", "Loại CTKM"),
    PROMOTION_LEVEL("PROMOTION_LEVEL", "promotionLevel", "Hạn Mức CTKM"),
    PROMOTION_RESULT("PROMOTION_RESULT", "promotionResult", "Loại Kết Quả KM"),

    LOYALTY_TYPE("LOYALTY_TYPE", "loyaltyType", "CTTB/CTTL"),
    LOYALTY_LEVEL("LOYALTY_LEVEL", "loyaltyLevel", "Hạn Mức TB/TL"),
    LOYALTY_PAID_TYPE("LOYALTY_PAID_TYPE", "loyaltyPaidType", "Loại Chi Trả"),
    LOYALTY_RESULT("LOYALTY_RESULT", "loyaltyResult", "Loại Kết Quả TB/TL"),

    SAMPLING_RESULT("SAMPLING_RESULT", "samplingResult", "Loại Kết Quả Sampling"),

    AUTO_PPO_CLAIM("AUTO_PPO_CLAIM", "autoPpoClaim", "PPO Claim Tự Động"),

    COACHING_TYPE("COACHING_TYPE", "coachingType", "Loại Thực Thi"),
    TOPIC_TYPE("TOPIC_TYPE", "topicType", "Chủ Đề"),
    TOPIC_DETAIL_TYPE("TOPIC_DETAIL_TYPE", "topicDetailType", "Chủ Đề Chi Tiết"),

    COACHING_NOTE_TYPE("COACHING_NOTE_TYPE", "coachingNoteType", "Chủ Đề Ghi Chú"),
    COACHING_REASON_TYPE("COACHING_REASON_TYPE", "coachingReasonType", "Lý Do Không Ghé Thăm"),

    IMG_HASH_TAG("IMG_HASH_TAG", "imageHashTag", "Nhãn Hình Ảnh"),
    IMG_HASH_TAG_AI("IMG_HASH_TAG_AI", "imageHashTag", "Nhãn Hình Ảnh AI"),

    CUSTOMER_NOTE_TOPIC_TYPE("CUSTOMER_NOTE_TOPIC_TYPE", "customerNoteTopicType", "Chủ Đề Ghi Chú"),
    DISTRIBUTOR_NOTE_TOPIC_TYPE("DISTRIBUTOR_NOTE_TOPIC_TYPE", "distributorNoteTopicType", "Chủ Đề Ghi Chú"),
    SALESMAN_NOTE_TOPIC_TYPE("SALESMAN_NOTE_TOPIC_TYPE", "salesManNoteTopicType", "Chủ Đề Ghi Chú");

    private final String code;

    @Getter
    private final String messageId;

    @Getter
    private final String defaultMsg;

    CommonCode(String code, String messageId, String defaultMsg) {
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
