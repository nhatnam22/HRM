package com.module.hrm.web.common.enumeration;

public final class AppConstants {

    /**
     * Initialize password
     */
    public static final String USM_PASSWORD_INIT_KEY = "USM_PASSWORD_INIT";
    public static final String SM_PASSWORD_INIT_KEY = "SM_PASSWORD_INIT";
    public static final String DISTRIBUTOR_PASSWORD_INIT_KEY = "DISTRIBUTOR_PASSWORD_INIT";

    /**
     * Customer need approval flag
     */
    public static final Boolean CUSTOMER_NEED_APPROVAL_FLAG = Boolean.TRUE;
    public static final String CUSTOMER_NEED_APPROVAL_FLAG_KEY = "CUSTOMER_NEED_APPROVAL_FLAG";

    /**
     * Customer modification need approval flag
     */
    public static final Boolean CUSTOMER_MODIFY_NEED_APPROVAL_FLAG = Boolean.TRUE;
    public static final String CUSTOMER_MODIFY_NEED_APPROVAL_FLAG_KEY = "CUSTOMER_MODIFY_NEED_APPROVAL_FLAG";

    /**
     * Customer modification gps need approval flag
     */
    public static final Boolean CUSTOMER_GPS_NEED_APPROVAL_FLAG = Boolean.TRUE;
    public static final String CUSTOMER_GPS_NEED_APPROVAL_FLAG_KEY = "CUSTOMER_GPS_NEED_APPROVAL_FLAG";

    /**
     * MCP need approval flag
     */
    public static final Boolean MCP_NEED_APPROVAL_FLAG = Boolean.TRUE;
    public static final String MCP_NEED_APPROVAL_FLAG_KEY = "MCP_NEED_APPROVAL_FLAG";

    /**
     * Loyalty need approval flag
     */
    public static final Boolean LOYALTY_NEED_APPROVAL_FLAG = Boolean.FALSE;
    public static final String LOYALTY_NEED_APPROVAL_FLAG_KEY = "LOYALTY_NEED_APPROVAL_FLAG";

    /**
     * Remote visit number (based on sales team)
     */
    public static final String REMOTE_VISIT_NUMBER_CULINARY_KEY = "REMOTE_VISIT_NUMBER_CULINARY";
    public static final String REMOTE_VISIT_NUMBER_BEVERAGE_KEY = "REMOTE_VISIT_NUMBER_BEVERAGE";
    public static final String REMOTE_VISIT_NUMBER_SEASONING_KEY = "REMOTE_VISIT_NUMBER_SEASONING";

    // Bussiness day
    public static final String MONDAY_WORKING = "MONDAY_WORKING";
    public static final String TUESDAY_WORKING = "TUESDAY_WORKING";
    public static final String WEDNESDAY_WORKING = "WEDNESDAY_WORKING";
    public static final String THURSDAY_WORKING = "THURSDAY_WORKING";
    public static final String FRIDAY_WORKING = "FRIDAY_WORKING";
    public static final String SATURDAY_WORKING = "SATURDAY_WORKING";
    public static final String SUNDAY_WORKING = "SUNDAY_WORKING";

    // T PLus
    public static final int T_PLUS_DAY = 2;
    public static final String SALES_OUT_T_PLUS = "SALES_OUT_T_PLUS";
    public static final String PURCHASE_T_PLUS = "PURCHASE_T_PLUS";
    public static final String INVENTORY_T_PLUS = "INVENTORY_T_PLUS";

    /**
     * MCP range routing
     */
    public static final int MCP_DAYS_RANGE = 14;
    public static final String MCP_DAYS_RANGE_KEY = "MCP_DAYS_RANGE";

    public static final double SALES_OUT_PERSONAL_INCOME_TAX = 1.5;
    public static final double SALES_OUT_PERSONAL_INCOME_TAX_10 = 1.5;
    public static final String SALES_OUT_PERSONAL_INCOME_TAX_KEY = "SALES_OUT_PERSONAL_INCOME_TAX";

    public static final double PROGRAM_SETTLEMENT_VAT = 8.0;

    //<------------------------MOBILE
    /**
     * IOS app version
     */
    public static final String IOS_APP_VERSION_KEY = "IOS_APP_VERSION";
    public static final String IOS_MT_VERSION_KEY = "IOS_MT_VERSION";

    /**
     * Android app version
     */
    public static final String ANDROID_APP_VERSION_KEY = "ANDROID_APP_VERSION";
    public static final String ANDROID_MT_VERSION_KEY = "ANDROID_MT_VERSION";

    /**
     * Coaching need approval flag
     */
    public static final Boolean COACHING_NEED_APPROVAL_FLAG = Boolean.FALSE;

    /**
     * Notify customer without sales out after 3 month
     */
    public static final int CUSTOMER_NOTIFY_WITHOUT_SALES_OUT_DAYS = 90;

    /**
     * Email
     */
    public static final String HR_EMAIL_ADDRESS = "HR_EMAIL_ADDRESS";
    public static final String DMS_EMAIL_ADDRESS = "DMS_EMAIL_ADDRESS";
    public static final String SC_MAIL_ADDRESS = "SC_MAIL_ADDRESS";
    public static final String SC_MAIL_ADDRESS_2 = "SC_MAIL_ADDRESS_2";

    /**
     * Device log over due date
     */
    public static final int DEVICE_LOG_OVER_DUE = 60;

    public static final String PURCHASE_ORDER_INVENTORY_RATE_MIN = "MIN";
    public static final String PURCHASE_ORDER_INVENTORY_RATE_MAX = "MAX";

    public static final int VANSALES_IMAGES_MIN = 3;

    public static final String COACHING_MAX_N_WW_IN_MONTH = "COACHING_MAX_N_WW_IN_MONTH";

    /**
     * Field work note
     */
    public static final Boolean NOTIFICATION_FIELD_WORK_NOTE_FLAG = Boolean.TRUE;

    private AppConstants() {}
}
