package com.module.hrm.web.common.utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.util.StringUtils;

public class CompareUtil {

    /**
     * Compare LocalDateTime with LocalDate
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareLocalDate(final LocalDate date1, final LocalDate date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("compare date1 and date2 has null");
        }

        return date1.compareTo(date2);
    }

    /**
     * Compare LocalDateTime with LocalDate
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean betweenLocalDate(final LocalDate date1, final LocalDate fromDate, final LocalDate toDate) {
        if (date1 == null || fromDate == null) {
            throw new RuntimeException("between date1 and fromDate has null");
        }

        if (toDate == null) {
            return compareLocalDate(date1, fromDate) >= 0;
        }

        return compareLocalDate(date1, fromDate) >= 0 && compareLocalDate(date1, toDate) <= 0;
    }

    /**
     * Compare LocalDateTime with LocalDate
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareLocalDateTime(final LocalDateTime date1, final LocalDateTime date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("compare date1 and date2 has null");
        }

        return date1.compareTo(date2);
    }

    /**
     * Compare two instant
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareInstant(final Instant instant1, final Instant instant2) {
        if (instant1 == null || instant2 == null) {
            throw new RuntimeException("compare date1 and date2 has null");
        }

        return instant1.compareTo(instant2);
    }

    /**
     * Compare LocalDateTime with LocalDate
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean betweenLocalDateTime(final LocalDateTime date1, final LocalDateTime fromDate, final LocalDateTime toDate) {
        if (date1 == null || fromDate == null) {
            throw new RuntimeException("between date1 and fromDate has null");
        }

        if (toDate == null) {
            return compareLocalDateTime(date1, fromDate) >= 0;
        }

        return compareLocalDateTime(date1, fromDate) >= 0 && compareLocalDateTime(date1, toDate) <= 0;
    }

    /**
     * Between two instant
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean betweenInstant(final Instant date1, final Instant fromDate, final Instant toDate) {
        if (date1 == null || fromDate == null) {
            throw new RuntimeException("between date1 and fromDate has null");
        }

        if (toDate == null) {
            return compareInstant(date1, fromDate) >= 0;
        }

        return compareInstant(date1, fromDate) >= 0 && compareInstant(date1, toDate) <= 0;
    }

    /**
     * compare instant date with now
     *
     * @param instant1
     * @return
     */
    public static boolean compareToDeactive(final Instant instant1) {
        if (instant1 == null) {
            return true;
        }

        return instant1.compareTo(Instant.now()) >= 0;
    }

    /**
     * compare two string
     */
    public static boolean equals2String(final String str1, final String str2) {
        if (str1 == null) {
            if (str2 == null) {
                return true;
            } else {
                return false;
            }
        }

        return str1.equals(str2);
    }

    /**
     * compare two long
     */
    public static int compareInteger(final Integer number1, final Integer number2) {
        if (number1 == null && number2 == null) {
            return 0;
        }

        if (number1 == null) {
            return -1;
        }

        if (number2 == null) {
            return 1;
        }

        return number1.compareTo(number2);
    }

    /**
     * compare two long
     */
    public static int compareLong(final Long number1, final Long number2) {
        if (number1 == null && number2 == null) {
            return 0;
        }

        if (number1 == null) {
            return -1;
        }

        if (number2 == null) {
            return 1;
        }

        return number1.compareTo(number2);
    }

    /**
     * compare two BigDecimal
     */
    public static int compareBigDecimal(final BigDecimal number1, final BigDecimal number2) {
        if (number1 == null && number2 == null) {
            return 0;
        }

        if (number1 == null) {
            return -1;
        }

        if (number2 == null) {
            return 1;
        }

        return number1.compareTo(number2);
    }

    /**
     * str between fromStr and toStr
     *
     * @param fromStr
     * @param toStr
     * @return
     */
    public static boolean betweenString(final String str, final String fromStr, final String toStr) {
        if (!StringUtils.hasText(str) || !StringUtils.hasText(fromStr)) {
            throw new RuntimeException("between str and fromStr has null");
        }

        if (!StringUtils.hasText(toStr)) {
            return str.compareTo(fromStr) >= 0;
        }

        return str.compareTo(fromStr) >= 0 && str.compareTo(toStr) <= 0;
    }

    private CompareUtil() {}
}
