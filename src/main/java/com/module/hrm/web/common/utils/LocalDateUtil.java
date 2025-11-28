package com.module.hrm.web.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateUtil {

    public static final ZoneId UTC = ZoneId.of("UTC");

    public static final String HO_CHI_MINH_ZONE_CHAR = "Asia/Ho_Chi_Minh";

    public static final ZoneId HO_CHI_MINH = ZoneId.of(HO_CHI_MINH_ZONE_CHAR);

    /**
     * Get local date time
     */
    public static LocalDateTime nowLocalDateTime() {
        return LocalDateTime.now(HO_CHI_MINH);
    }

    /**
     * Get local date time
     */
    public static LocalDateTime nowLocalDateTime(ZoneId zoneId) {
        return LocalDateTime.now(zoneId);
    }

    /**
     * Get local date
     */
    public static LocalDate nowLocalDate() {
        return LocalDate.now(HO_CHI_MINH);
    }

    /**
     * Get local date time from instant
     */
    public static LocalDateTime of(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, HO_CHI_MINH);
    }

    /**
     *
     * Get Last Day Of Previous Month
     */
    public static LocalDate getLastDayOfPreviousMonth() {
        return nowLocalDate().withDayOfMonth(1).minusDays(1);
    }

    /**
     * getEpochMilli
     *
     * @param localDateTime
     * @return
     */
    public static long getEpochMilli(LocalDateTime localDateTime) {
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, HO_CHI_MINH);
        long epochMilli = zdt.toInstant().toEpochMilli();

        return epochMilli;
    }

    /**
     *
     * Get local date time if null
     */
    public static LocalDate defaultNowIfNull(LocalDate value) {
        if (value != null) return value;

        return nowLocalDate();
    }

    public static int getNumOfDays(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) return 0;

        return Period.between(fromDate, toDate).getDays() + 1;
    }

    public static int getNumOfNights(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) return 0;

        return Period.between(fromDate, toDate).getDays();
    }

    private LocalDateUtil() {}
}
