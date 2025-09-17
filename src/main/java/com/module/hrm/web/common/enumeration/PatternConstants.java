package com.module.hrm.web.common.enumeration;

public final class PatternConstants {

    public static final String SLASH_CHARACTER = "/";
    public static final String HYPHEN_CHARACTER = "-";
    public static final String HYPHEN_CHARACTER_UNDER = "_";

    public static final char CHAR_SEMICOLON = ';';

    /**
     * Max length
     */
    public static final int PASSWORD_MIN_LENGTH = 6;
    public static final int PASSWORD_MAX_LENGTH = 50;
    public static final int PHONE_MAX_LENGTH = 12;
    public static final int CODE_MAX_LENGTH = 50;
    public static final int ADDRESS_MAX_LENGTH = 80;
    public static final int NAME_MAX_LENGTH = 256;
    public static final int NAME_512_MAX_LENGTH = 512;
    public static final int NAME_1024_MAX_LENGTH = 1024;
    public static final int NAME_4096_MAX_LENGTH = 4096;

    // FORMAT: MMyyyy
    public static final int YEAR_MONTH_LENGTH = 6;

    /**
     * Vietnam GPS
     */
    public static final int LONGITUDE_MIN = 102;
    public static final int LONGITUDE_MAX = 110;
    public static final int LATITUDE_MIN = 8;
    public static final int LATITUDE_MAX = 24;

    /**
     * Date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_2 = "yyyyMMdd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT_2 = "yyyy-MM-dd HH:mm";

    public static final String YEAR_MONTH_FORMAT = "yyyyMM";

    public static final String TIME_FORMAT = "HH:mm";
    public static final String TIME_FORMAT_2 = "HH:mm:ss";

    /**
     * Date format for local
     */
    public static final String LOCAL_DATE_FORMAT = "dd/MM/yyyy";
    public static final String LOCAL_DATE_FORMAT_2 = "dd.MM.yyyy";
    public static final String LOCAL_DATE_FILE_FORMAT = "ddMMyyyy";

    public static final String LOCAL_DATE_TIME_FILE_FORMAT = "ddMMyyyyHHmmss";
    public static final String LOCAL_DATE_TIME_FILE_FORMAT_2 = "dd.MM.yyyy HH:mm";
    public static final String LOCAL_DATE_TIME_FILE_FORMAT_3 = "dd/MM/yyyy HH:mm";
    public static final String LOCAL_DATE_TIME_FILE_FORMAT_4 = "yyyyMMddHHmm";

    public static final String LOCAL_MONTH_YEAR_FORMAT = "MM/yyyy";
    public static final String LOCAL_MONTH_YEAR_FORMAT_2 = "MM.yyyy";
    public static final String LOCAL_DATE_MONTH_FORMAT = "dd-MMM"; // 27-Feb

    /**
     * Export Filename
     */
    public static final String TERRITORY_FILENAME = "SDMS_territory";
    public static final String USER_FILENAME = "SDMS_user";
    public static final String SURVEY_FILENAME = "SDMS_survey";

    public static final String CUSTOMER_FILENAME = "SDMS_KH";
    public static final String CUSTOMER_COOLER_FILENAME = "SDMS_KH_COOLER";
    public static final String CUSTOMER_HIERARCHY_FILENAME = "SDMS_KH_HIERARCHY";
    public static final String VISIT_PLAN_FILENAME = "SDMS_MCP";
    public static final String PRODUCT_FILENAME = "SDMS_SP";
    public static final String VISIT_PLAN_GPS_FILENAME = "SDMS_MCP_GPS";

    public static final String SALES_OUT_FILENAME = "SDMS_DHB";
    public static final String PURCHASE_INVOICE_FILENAME = "SDMS_DHM";
    public static final String INVENTORY_FILENAME = "SDMS_BCTK";

    public static final String INVENTORY_JOURNAL_FILENAME = "SDMS_PDCK";

    public static final String PROGRAM_IO_SAP_FILENAME = "SDMS_IOSAP";
    public static final String SALES_ROUTE_FILENAME = "SDMS_TBH";
    public static final String PURCHASE_FILENAME = "SDMS_MH";
    public static final String PROGRAM_FILENAME = "SDMS_HTTM";

    public static final String SALESOUT_LOYALTY_FILENAME = "SDMS_TBTL_%s";
    public static final String CUSTOMER_INACTIVE = "SDMS_CUSTOMER_INACTIVE_%s";
    public static final String SALES_FORCE_FILENAME = "SDMS_SALESFORCE_%s";

    public static final String SI_TARGET_FILENAME = "SDMS_SITarget";

    public static final String LOYALTY_CUSTOMER_FILENAME = "SDMS_KH_TBTL";

    public static final String PROMOTION_CUSTOMER_FILENAME = "SDMS_KH_KM";

    public static final String ALLOCATION_FILENAME = "SDMS_ALLOCATION_%s_%s";
    public static final String ALLOCATION_CLAIM_FILENAME = "SDMS_ALLOCATION_CLAIM_%s";

    public static final String SUPPLY_PLAN_FILENAME = "SDMS_SUPPLY_PLAN";

    /**
     * Day number
     */
    public static final Integer MONDAY = 1;
    public static final Integer TUESDAY = 2;
    public static final Integer WEDNESDAY = 3;
    public static final Integer THURSDAY = 4;
    public static final Integer FRIDAY = 5;
    public static final Integer SATURDAY = 6;
    public static final Integer SUNDAY = 7;

    /**
     * Quantity
     */
    public static final long SALES_OUT_MAX_QUANTITY = 999999;
    public static final long SALES_OUT_MAX_DISCOUNT_RATE = 100;
    public static final long SALES_OUT_MAX_DISCOUNT_AMOUNT = 999999999;
    public static final int SALES_OUT_MAX_RATE = 29999;

    /**
     * Loyalty
     */
    public static final int IMAGE_UPLOAD_MAX = 3;

    public static final int MOBILE_PHOTO_NUMBER_MAX = 5;

    /**
     * Time format for local
     */
    public static final String LOCAL_TIME_FORMAT = "HH:mm:ss";

    /**
     * Currency unit
     */
    public static final String VND_MARK = "đ";

    /**
     * device
     */
    public static final String PC_DEVICE = "PC";
    public static final String MOBILE_DEVICE = "MOBILE";

    /**
     * Mobile diff seconds time
     */
    public static final int MOBILE_SECONDS_PLUS_TIME = 120;

    /**
     * Field work radius in meters
     */
    public static final int FIELD_WORK_RADIUS_M_UNIT = 500;

    public static final String IMG_HASH_TAG_LIKE = "like";
    public static final String IMG_HASH_TAG_DISLIKE = "dislike";

    private PatternConstants() {}
}
