package com.module.hrm.security;

import com.module.hrm.config.Constants;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    /**
     * User role
     */
    public static final String NOT_PUBLIC = "ROLE_NOT_PUBLIC";
    public static final String ROOT = "ROLE_ROOT";

    //public static final String ADMIN = "ROLE_ADMIN";

    public static final String TRADE = "ROLE_TRADE";
    public static final String SALES_CAP = "ROLE_SALES_CAP";
    public static final String SALES_COMPLIANCE = "ROLE_SALES_COMPLIANCE";
    public static final String SALES_OP = "ROLE_SALES_OPERATION";
    public static final String MARKETING = "ROLE_MARKETING";

    public static final String ADMIN_DISTRIBUTOR = "ROLE_ADMIN_DISTRIBUTOR";

    public static final String SD_ASM = "ROLE_SD_ASM";
    public static final String SUPERVISOR = "ROLE_SUPERVISOR";
    public static final String MT_SUPERVISOR = "ROLE_MT_SUPERVISOR";
    public static final String SALES_MAN = "ROLE_SALES_MAN";

    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Group and Role mapping
     */
    public static final Map<String, String> GROUP_ROLE_MAP = Stream.of(
        new AbstractMap.SimpleEntry<>(Constants.ADMIN_DISTRIBUTOR_GROUP_CODE, ADMIN_DISTRIBUTOR),
        new AbstractMap.SimpleEntry<>(Constants.SD_GROUP_CODE, SD_ASM),
        new AbstractMap.SimpleEntry<>(Constants.ASM_GROUP_CODE, SD_ASM),
        new AbstractMap.SimpleEntry<>(Constants.SUP_GROUP_CODE, SUPERVISOR),
        new AbstractMap.SimpleEntry<>(Constants.SALES_MAN_GROUP_CODE, SALES_MAN)
    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    /**
     * Sales Manager group code
     */
    public static final List<String> SALES_MANAGER_GROUPS = Collections.unmodifiableList(
        Arrays.asList(Constants.SD_GROUP_CODE, Constants.ASM_GROUP_CODE, Constants.SUP_GROUP_CODE)
    );

    /**
     * Allow role to register user
     */
    public static final List<String> SYSTEM_ROLE = new ArrayList<>(List.of(ROOT));

    public static final List<String> ALLOWED_ROLE = new ArrayList<>(
        List.of(TRADE, SALES_CAP, SALES_COMPLIANCE, SALES_OP, MARKETING, ADMIN_DISTRIBUTOR, SD_ASM, SUPERVISOR, MT_SUPERVISOR, SALES_MAN)
    );

    private AuthoritiesConstants() {}
}
