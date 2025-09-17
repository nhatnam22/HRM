package com.module.hrm.web.common.enumeration;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Excel templates
 */
public final class TemplateConstants {

    // Excel Template Directory
    public static final String EXCEL_TEMPLATE_DIRECTORY = "templates/excel";

    // Template Directory
    public static final String TEMPLATE_DIRECTORY = "templates/";

    // PDF Template Directory
    public static final String PDF_TEMPLATE_DIRECTORY = "pdf/";

    public static final String EXCEL_EXTENSION = ".xlsx";

    public static final String CSV_EXTENSION = ".csv";

    public static final String PDF_EXTENSION = ".pdf";

    public static final String FONT_DIRECTORY = "templates/pdf/font/";

    public static final String PDF_TEMPLATE_SUFFIX = ".html";

    public static final String PDF_TEMPLATE_MODE = "HTML5";

    public static final String ENCODING_UTF_8 = "UTF-8";
    // PDF Font
    public static final String FONT_TAHOMA = "tahoma.ttf";

    public static final String PDF_KEY_MAP_DATAS = "datas";

    public static final String EXTENSION_DISPLAY_LOYALTY = "TB";

    public static final String EXTENSION_EARNING_LOYALTY = "TL";

    /**
     * Templates
     */
    // DELIVERY_NOTE
    public static final String TEMPLATE_DELIVERY_NOTE = "delivery-note";
    public static final String FILENAME_DELIVERY_NOTE = "VIETCOS_PXK_%s";

    // DELIVERY_NOTE_BILLING
    public static final String TEMPLATE_DELIVERY_NOTE_BILLING = "delivery-note-billing";
    public static final String FILENAME_DELIVERY_NOTE_BILLING = "VIETCOS_PGNTT_%s";

    public static final String TEMPLATE_DAILY_SALES_OUT = "daily-sales-out";
    public static final String FILENAME_DAILY_SALES_OUT = "VIETCOS_DSBHTN_%s";

    public static final String TEMPLATE_SALES_OUT_BY_ROUTE = "sales-out-by-route";
    public static final String FILENAME_SALES_OUT_BY_ROUTE = "VIETCOS_SLBTTBH_%s";

    public static final String TEMPLATE_SALES_OUT_BY_CUSTOMER = "sales-out-by-customer";
    public static final String FILENAME_SALES_OUT_BY_CUSTOMER = "VIETCOS_SLBTTBH_%s";

    public static final String TEMPLATE_PURCHASE_INVOICE_DETAIL = "purchase-invoice-detail";
    public static final String FILENAME_PURCHASE_INVOICE_DETAIL = "VIETCOS_BCCTHHMV_%s";

    public static final String TEMPLATE_INVENTORY_IN_OUT = "inventory-in-out";
    public static final String FILENAME_INVENTORY_IN_OUT = "VIETCOS_BCXNT_%s";

    public static final String TEMPLATE_REQUEST_PAYMENT = "request-payment";
    public static final String FILENAME_REQUEST_PAYMENT = "VIETCOS_GDNTT";

    public static final String TEMPLATE_COMMERCIAL_SUPPORT = "commercial-support";
    public static final String FILENAME_COMMERCIAL_SUPPORT = "VIETCOS_QTCTKM_%s";

    public static final String TEMPLATE_EXHIBITION_DETAIL = "exhibition-detail";
    public static final String FILENAME_EXHIBITION_DETAIL = "VIETCOS_QTCT%s_%s";

    public static final String TEMPLATE_LOYALTY_FINAL_SETTLEMENT = "loyalty-final-settlement";
    public static final String FILENAME_LOYALTY_FINAL_SETTLEMENT = "VIETCOS_QT%s_%s";

    public static final String TEMPLATE_LOYALTY_PAYMENT_PROGRESS = "loyalty-payment-progress";
    public static final String FILENAME_LOYALTY_PAYMENT_PROGRESS = "VIETCOS_TDTTCT%s_%s";

    public static final String TEMPLATE_PRODUCT_PURCHASE_INVOICE = "product-purchase-invoice";
    public static final String FILENAME_PRODUCT_PURCHASE_INVOICE = "VIETCOS_THHHMV";

    public static final String TEMPLATE_COACHING = "coaching";
    public static final String FILENAME_COACHING = "VIETCOS_COACHING";

    public static final String TEMPLATE_DISTRIBUTOR_PROGRAM_SETTLEMENT = "distributor-program-settlement";
    public static final String FILENAME_DISTRIBUTOR_PROGRAM_SETTLEMENT = "VIETCOS_QT_CTKM_%s";

    public static final String TEMPLATE_PROGRAM_SETTLEMENT = "program-settlement";
    public static final String FILENAME_PROGRAM_SETTLEMENT = "VIETCOS_QT_CTKM";

    public static final String TEMPLATE_SETTLEMENT_INVOICE = "settlement-invoice";
    public static final String FILENAME_SETTLEMENT_INVOICE = "VIETCOS_HDQT_CTKM";

    public static final String TEMPLATE_COACHING_HO = "coaching-ho";
    public static final String FILENAME_COACHING_HO = "VIETCOS_COACHING_HO";

    public static final String TEMPLATE_SAMPLING_NOTE = "sampling-note";
    public static final String FILENAME_SAMPLING_NOTE = "VIETCOS_SL_PXK_%s";

    public static final String TEMPLATE_SAMPLING_SETTLEMENT = "sampling-settlement";
    public static final String FILENAME_SAMPLING_SETTLEMENT = "VIETCOS_SL_PQT_%s";

    public static final String TEMPLATE_VANSALES_NOTE = "vansales-note";
    public static final String FILENAME_VANSALES_NOTE = "VIETCOS_VS_PXK_%s";

    public static final String TEMPLATE_VANSALES_SETTLEMENT = "vansales-settlement";
    public static final String FILENAME_VANSALES_SETTLEMENT = "VIETCOS_VS_PQT_%s";

    public static final String TEMPLATE_SAMPLING_DISTRIBUTOR = "sampling-distributor";
    public static final String FILENAME_SAMPLING_DISTRIBUTOR = "VIETCOS_SL_%s";

    public static final String TEMPLATE_PROMOTION_CASH = "promotion-cash";
    public static final String FILENAME_PROMOTION_CASH = "VIETCOS_HTTMTIEN";

    public static final String TEMPLATE_FIELDWORK_NOTE = "fieldwork-note";
    public static final String FILENAME_FIELDWORK_NOTE = "VIETCOS_FW";

    public static final Map<String, String> TEMPLATE_FILENAME_MAP = Stream.of(
        new AbstractMap.SimpleEntry<>(TEMPLATE_DELIVERY_NOTE, FILENAME_DELIVERY_NOTE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_DELIVERY_NOTE_BILLING, FILENAME_DELIVERY_NOTE_BILLING),
        new AbstractMap.SimpleEntry<>(TEMPLATE_DAILY_SALES_OUT, FILENAME_DAILY_SALES_OUT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SALES_OUT_BY_ROUTE, FILENAME_SALES_OUT_BY_ROUTE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SALES_OUT_BY_CUSTOMER, FILENAME_SALES_OUT_BY_CUSTOMER),
        new AbstractMap.SimpleEntry<>(TEMPLATE_PURCHASE_INVOICE_DETAIL, FILENAME_PURCHASE_INVOICE_DETAIL),
        new AbstractMap.SimpleEntry<>(TEMPLATE_INVENTORY_IN_OUT, FILENAME_INVENTORY_IN_OUT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_REQUEST_PAYMENT, FILENAME_REQUEST_PAYMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_COMMERCIAL_SUPPORT, FILENAME_COMMERCIAL_SUPPORT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_EXHIBITION_DETAIL, FILENAME_EXHIBITION_DETAIL),
        new AbstractMap.SimpleEntry<>(TEMPLATE_LOYALTY_FINAL_SETTLEMENT, FILENAME_LOYALTY_FINAL_SETTLEMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_LOYALTY_PAYMENT_PROGRESS, FILENAME_LOYALTY_PAYMENT_PROGRESS),
        new AbstractMap.SimpleEntry<>(TEMPLATE_PRODUCT_PURCHASE_INVOICE, FILENAME_PRODUCT_PURCHASE_INVOICE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_COACHING, FILENAME_COACHING),
        new AbstractMap.SimpleEntry<>(TEMPLATE_DISTRIBUTOR_PROGRAM_SETTLEMENT, FILENAME_DISTRIBUTOR_PROGRAM_SETTLEMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_PROGRAM_SETTLEMENT, FILENAME_PROGRAM_SETTLEMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SETTLEMENT_INVOICE, FILENAME_SETTLEMENT_INVOICE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_COACHING_HO, FILENAME_COACHING_HO),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SAMPLING_NOTE, FILENAME_SAMPLING_NOTE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SAMPLING_SETTLEMENT, FILENAME_SAMPLING_SETTLEMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_VANSALES_NOTE, FILENAME_VANSALES_NOTE),
        new AbstractMap.SimpleEntry<>(TEMPLATE_VANSALES_SETTLEMENT, FILENAME_VANSALES_SETTLEMENT),
        new AbstractMap.SimpleEntry<>(TEMPLATE_SAMPLING_DISTRIBUTOR, FILENAME_SAMPLING_DISTRIBUTOR),
        new AbstractMap.SimpleEntry<>(TEMPLATE_PROMOTION_CASH, FILENAME_PROMOTION_CASH),
        new AbstractMap.SimpleEntry<>(TEMPLATE_FIELDWORK_NOTE, FILENAME_FIELDWORK_NOTE)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    /**
     * Excel Columns
     */
    public static final int COL_A = 0;

    public static final int COL_B = 1;

    public static final int COL_C = 2;

    public static final int COL_D = 3;

    public static final int COL_E = 4;

    public static final int COL_F = 5;

    public static final int COL_G = 6;

    public static final int COL_H = 7;

    public static final int COL_I = 8;

    public static final int COL_J = 9;

    public static final int COL_K = 10;

    public static final int COL_L = 11;

    public static final int COL_M = 12;

    public static final int COL_N = 13;

    public static final int COL_O = 14;

    public static final int COL_P = 15;

    public static final int COL_Q = 16;

    public static final int COL_R = 17;

    public static final int COL_S = 18;

    public static final int COL_T = 19;

    public static final int COL_U = 20;

    public static final int COL_V = 21;

    public static final int COL_W = 22;

    public static final int COL_X = 23;

    public static final int COL_Y = 24;

    public static final int COL_Z = 25;

    public static final int COL_AA = 26;

    public static final int COL_AB = 27;

    public static final int COL_AC = 28;

    public static final int COL_AD = 29;

    public static final int COL_AE = 30;

    public static final int COL_AF = 31;

    public static final int COL_AG = 32;

    public static final int COL_AH = 33;

    public static final int COL_AI = 34;

    public static final int COL_AJ = 35;

    public static final int COL_AK = 36;

    public static final int COL_AL = 37;

    public static final int COL_AM = 38;

    public static final int COL_AN = 39;

    public static final int COL_AO = 40;

    public static final int COL_AP = 41;

    public static final int COL_AQ = 42;

    public static final int COL_AR = 43;

    public static final int COL_AS = 44;

    public static final int COL_AT = 45;

    public static final int COL_AU = 46;

    public static final int COL_AV = 47;

    public static final int COL_AW = 48;

    public static final int COL_AX = 49;

    public static final int COL_AY = 50;

    public static final int COL_AZ = 51;

    public static final int COL_BA = 52;

    public static final int COL_BB = 53;

    public static final int COL_BC = 54;

    public static final int COL_BD = 55;

    public static final int COL_BE = 56;

    public static final int COL_BF = 57;

    public static final int COL_BG = 58;

    public static final int COL_BH = 59;

    public static final int COL_BI = 60;

    public static final int COL_BJ = 61;

    public static final int COL_BK = 62;

    public static final int COL_BL = 63;

    public static final String CHAR_HYPHEN = "-";

    public static final String CHAR_COLON = ":";

    private TemplateConstants() {}
}
