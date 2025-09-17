package com.module.hrm.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static final String CODE_REGEX = "^[a-zA-Z0-9_]+$";
    public static final String INVOICE_CODE_REGEX = "^[A-Z0-9]{7}\\.[0-9]+$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "vi";

    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String ADMIN_EMAIL = "noreply@smartdms.vn";

    public static final String VIETNAM_COUNTRY_CODE = "VN";

    /**
     * URL value
     */
    public static final String USER_API_URL = "/api";
    public static final String ADMIN_API_URL = "/api/admin";
    public static final String MOBILE_API_URL = "/api/m";

    public static final String FIELD_WORKS_API_URL = "/api/fw";
    public static final String MANAGEMENT_API_URL = "/api/management";
    public static final String SALES_API_URL = "/api/sales";
    public static final String REPORT_API_URL = "/api/report";
    public static final String SERVICE_API_ZALO = "/api/service/zalo";
    public static final String EXPENSE_API_URL = "/api/expense";
    public static final String APPROVAL_API_URL = "/api/approval";
    public static final String SALES_FORCE_URL = "/api/sales-forces";

    /**
     * Default value
     */
    public static final String DEFAULT_DISTRIBUTOR_CODE = "NPP00001";
    //public static final String TESTING_DISTRIBUTOR_CODE = "TESTING";

    public static final String ALL_CUSTOMER_CODE = "ALL_CUSTOMER";
    public static final String NEW_CUSTOMER_CODE = "NEW_CUSTOMER";
    public static final String MT_CUSTOMER_CODE = "MT_CUSTOMER";
    public static final String GT_CUSTOMER_CODE = "GT_CUSTOMER";
    public static final String TEMP_CUSTOMER_CODE = "MT";
    public static final Long ALL_CUSTOMER_ID = Long.valueOf(0);

    /**
     * Group code
     */
    public static final String SYSTEM_GROUP_CODE = "SYSTEM";
    public static final String ADMIN_GROUP_CODE = "ADMIN";

    public static final String ADMIN_DISTRIBUTOR_GROUP_CODE = "ADMIN_DISTRIBUTOR";

    public static final String SD_GROUP_CODE = "SD";
    public static final String ASM_GROUP_CODE = "ASM";
    public static final String SUP_GROUP_CODE = "SUPERVISOR";
    public static final String SALES_MAN_GROUP_CODE = "SALES_MAN";

    /**
     * File constants
     */
    public static final String DEFAULT_UPLOAD_DIR = "default";
    public static final String BACKUP_UPLOAD_DIR = "backup";
    public static final String SYNC_UPLOAD_DIR = "sync";
    public static final String TEMP_UPLOAD_DIR = "temp";
    public static final String USERS_DECOMPRESS_DIR = "users";

    public static final String CSV_DIR = "csv";
    public static final String SALES_ACTUAL_DIR = "salesActual";
    public static final String AVG_SO_L3M_DIR = "avgSOL3M";
    public static final String CSV_FILE_NAME = "Percentile_";

    // Radius of earth in kilometers. Use 3956 for miles, 6371 for km
    public static final int AVERAGE_RADIUS_OF_EARTH = 6371;
    public static final int KM_UNIT_CONVERTER = 1000;

    // AUTO note
    public static final String AUTO_CANCEL = "AUTO CANCEL";

    private Constants() {}
}
