package com.module.hrm.web.common.utils;

import static com.module.hrm.web.common.utils.LocalDateUtil.HO_CHI_MINH;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.module.hrm.web.common.enumeration.AppConstants;
import com.module.hrm.web.common.enumeration.BaseEnum;
import com.module.hrm.web.common.enumeration.BusinessType;
import com.module.hrm.web.common.enumeration.PatternConstants;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class ConvertUtil {

    public static final long M_UNIT = 1000000L;
    public static final long T_UNIT = 1000L;

    public static final String COMMA_SPLIT = ",";

    public static final String HYPHEN_JOIN = " - ";
    public static final String ADDRESS_COMMA_JOIN = ", ";
    public static final String SPACE_SPLIT = " ";

    private static final Map<String, String> mapVendor = Map.of("A100", "Trụ Sở Chính", "A200", "CN Hưng Yên");

    private static final Map<String, String> mapUnibenTax = Map.of("0301442989", "A100", "0301442989-006", "A200");

    /**
     * read number to string
     *
     * @param value
     * @return
     */
    public static String convertNumberToText(Long value) {
        String output = "";
        try {
            NumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale("vi", "VN"), RuleBasedNumberFormat.SPELLOUT);
            output = ruleBasedNumberFormat.format(value) + " đồng";
        } catch (Exception e) {
            output = "Không đồng";
        }
        return output.substring(0, 1).toUpperCase() + output.substring(1);
    }

    public static String formatMoney(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            value = 0;
        }
        String output = "";
        try {
            NumberFormat ruleBasedNumberFormat = NumberFormat.getNumberInstance();
            output = ruleBasedNumberFormat.format(value);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at formatMoney : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
        return output;
    }

    /**
     * convert to Base Enum by csv name
     *
     * @param clazz
     * @param name
     * @return
     */
    public static <T extends BaseEnum> T valueOfCsv(Class<T> clazz, String name) {
        if (!StringUtils.hasText(name)) {
            return null;
        }

        T[] values = (T[]) clazz.getEnumConstants();
        return Stream.of(values).filter(v -> v.getValue().equals(name)).findFirst().orElse(null);
    }

    /**
     * convert to integer value
     *
     * @param value
     * @return
     */
    public static Integer toInteger(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        return Integer.valueOf(value.toString());
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static Integer toIntegerOrZero(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return Integer.valueOf(0);
        }

        if (value instanceof Integer) {
            return (Integer) value;
        }

        return Integer.valueOf(value.toString());
    }

    /**
     * convert to long value
     *
     * @param value
     * @return
     */
    public static Long toLong(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return Long.valueOf(value.toString());
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static Long toLongOrZero(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return Long.valueOf(0);
        }

        if (value instanceof Long) {
            return (Long) value;
        }

        return Long.valueOf(value.toString());
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static Long toLongOrZero(BigDecimal value) {
        if (ObjectUtils.isEmpty(value)) {
            return Long.valueOf(0);
        }

        return (Long) value.longValue();
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static Long toAmountOrZero(BigDecimal value) {
        if (ObjectUtils.isEmpty(value)) {
            return Long.valueOf(0);
        }

        return Math.round(value.doubleValue());
    }

    /**
     * convert to double value
     *
     * @param value
     * @return
     */
    public static Double toDouble(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Double) {
            return (Double) value;
        }

        return Double.valueOf(value.toString());
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static double toDoubleOrZero(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return 0.0;
        }

        if (value instanceof Double) {
            return (Double) value;
        }

        return Double.valueOf(value.toString());
    }

    /**
     * convert to long or zero value
     *
     * @param value
     * @return
     */
    public static BigDecimal toBigDecimalOrZero(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return BigDecimal.ZERO;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        return new BigDecimal(value.toString());
    }

    /**
     * convert to string value
     *
     * @param value
     * @return
     */
    public static String toString(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return "";
        }

        if (value instanceof String) {
            return (String) value;
        }

        return value.toString();
    }

    /**
     * convert to boolean value
     *
     * @param value
     * @return
     */
    public static Boolean toBoolean(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return Arrays.asList("1", "true", "x").contains(value.toString().toLowerCase());
    }

    /**
     * convert string to lower
     *
     * @param
     * @return
     */
    public static String camelCaseToLowerHyphen(String value) {
        if (StringUtils.hasText(value)) {
            // Remove special character
            String validString = value.replaceAll("[^a-zA-Z0-9]+", "");

            // Regular Expression
            String regex = "([a-z])([A-Z]+)";
            String replacement = "$1_$2";

            return validString.replaceAll(regex, replacement).toLowerCase();
        }

        return null;
    }

    /**
     * convert string to Local Date with format
     *
     * @param value
     * @return
     */
    public static LocalDate toLocalDate(String value, String pattern) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(LocalDateUtil.UTC);
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at parsing string to date : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert string to Local Date
     *
     * @param value
     * @return
     */
    public static LocalDate toLocalDate(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        return toLocalDate(value.toString(), PatternConstants.DATE_FORMAT);
    }

    /**
     * convert string to Local Date
     *
     * @param value
     * @return
     */
    public static YearMonth toYearMonth(Object value, String pattern) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(LocalDateUtil.UTC);
            return YearMonth.parse(value.toString(), formatter);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at parsing string to YearMonth : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert string to Local Date Time
     *
     * @param value
     * @return
     */
    public static LocalDateTime toLocalDateTime(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Timestamp) {
            return ((Timestamp) value).toInstant().atZone(LocalDateUtil.UTC).toLocalDateTime();
        }

        throw new RuntimeException("toLocalDateTime is not timestamp");
    }

    public static LocalDateTime toLocalDateTimeUTC(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        if (value instanceof Instant) {
            return ((Instant) value).atZone(LocalDateUtil.UTC).toLocalDateTime();
        }

        throw new RuntimeException("toLocalDateTime is not timestamp");
    }

    /**
     * convert date time to instant
     *
     * @param
     * @return
     */
    public static Instant toInstant(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        return toLocalDateTime(value.toString().substring(0, PatternConstants.DATE_TIME_FORMAT.length()), PatternConstants.DATE_TIME_FORMAT)
            .atZone(LocalDateUtil.UTC)
            .toInstant();
    }

    public static Instant toInstantUTC(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        return toUTCDateTime(value.toString().substring(0, PatternConstants.DATE_TIME_FORMAT.length()), PatternConstants.DATE_TIME_FORMAT)
            .atZone(LocalDateUtil.HO_CHI_MINH)
            .withZoneSameInstant(LocalDateUtil.UTC)
            .toInstant();
    }

    /**
     * convert date time to instant
     *
     * @param
     * @return
     */
    public static Instant toInstant(Object value, String pattern) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        return toLocalDateTime(value.toString().substring(0, pattern.length()), pattern).atZone(LocalDateUtil.UTC).toInstant();
    }

    /**
     * convert fromDate to date time UTC
     *
     * @param fromDate
     * @return
     */
    public static Instant fromDateTimeUtc(Object fromDate) {
        if (ObjectUtils.isEmpty(fromDate)) {
            return null;
        }

        return toLocalDate(fromDate).atStartOfDay(HO_CHI_MINH).toInstant();
    }

    /**
     * convert toDate to date time UTC
     *
     * @param toDate
     * @return
     */
    public static Instant toDateTimeUtc(Object toDate) {
        if (ObjectUtils.isEmpty(toDate)) {
            return null;
        }

        return toLocalDate(toDate).atStartOfDay(HO_CHI_MINH).plusDays(1).toInstant();
    }

    /**
     * convert string to Local Date Time with format
     */
    private static LocalDateTime toLocalDateTime(String value, String pattern) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(LocalDateUtil.UTC);
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at parsing string to date : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static LocalDateTime toUTCDateTime(String value, String pattern) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(LocalDateUtil.HO_CHI_MINH);
            return LocalDateTime.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at parsing string to date : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert object to json
     *
     * @param value
     * @return json string
     */
    public static String toJson(Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            ObjectWriter ow = new ObjectMapper().writer();
            return ow.writeValueAsString(value);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at convert to json : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert object to json
     *
     * @param value
     * @return json string
     */
    public static <T> T toObject(Object value, Class<T> valueType) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            ObjectReader or = new ObjectMapper().reader();
            return or.readValue(value.toString(), valueType);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at convert to object : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert object to json
     *
     * @param value
     * @return json string
     */
    public static <T> T toObject(Object value, TypeReference<T> valueTypeRef) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.readValue(value.toString(), valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at convert to object : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert object to json
     *
     * @param value
     * @return json string
     */
    public static <T> T toObject(Object value, Class<T> valueType, String jsonNode) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String strValue = value.toString();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (StringUtils.hasText(jsonNode)) {
                JsonNode rootNode = objectMapper.readTree(value.toString());
                JsonNode bodyNode = rootNode.get(jsonNode);
                if (ObjectUtils.isEmpty(bodyNode)) return null;

                strValue = bodyNode.asText();
            }

            ObjectReader or = objectMapper.reader();
            return or.readValue(strValue, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at convert to object : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * convert object to class object
     *
     * @param value
     * @return json string
     */
    public static <T> T toObjectClass(Object value, Class<T> valueType) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String str = objectMapper.writeValueAsString(value);
            ObjectReader or = objectMapper.reader();
            return or.readValue(str, valueType);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at convert to object : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * format instant time with pattern
     *
     * @param instant
     * @return
     */
    public static String instantToString(Instant instant, String pattern) {
        if (instant == null) {
            return "";
        }

        String formatPattern = PatternConstants.DATE_TIME_FORMAT;
        if (StringUtils.hasText(pattern)) {
            formatPattern = pattern;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern).withZone(LocalDateUtil.UTC);
        return formatter.format(instant);
    }

    /**
     * format date time with pattern
     *
     * @param datetime
     * @return
     */
    public static String datetimeToString(LocalDateTime datetime, String pattern) {
        if (datetime == null) {
            return "";
        }

        String formatPattern = PatternConstants.DATE_TIME_FORMAT;
        if (StringUtils.hasText(pattern)) {
            formatPattern = pattern;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return datetime.format(formatter);
    }

    /**
     * format date time with pattern
     *
     * @param
     * @return
     */
    public static String dateToString(LocalDate date, String pattern) {
        if (date == null) {
            return "";
        }

        String formatPattern = PatternConstants.DATE_FORMAT;
        if (StringUtils.hasText(pattern)) {
            formatPattern = pattern;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return date.format(formatter);
    }

    /**
     * format date time with ISO default pattern
     *
     * @param
     * @return
     */
    public static String dateToString(LocalDate date) {
        return dateToString(date, PatternConstants.DATE_FORMAT);
    }

    /**
     * concat address
     */
    public static String concatAddress(Object... address) {
        return concat(ADDRESS_COMMA_JOIN, address);
    }

    /**
     * concat object
     */
    public static String concat(String delimiter, Object... obj) {
        String[] str = Arrays.stream(obj).filter(x -> !ObjectUtils.isEmpty(x)).map(String::valueOf).toArray(String[]::new);

        return String.join(delimiter, str);
    }

    /**
     * append with prefix
     */
    public static String appendWithPrefix(int length, String prefix, int index) {
        String indexStr = String.valueOf(index);
        if (indexStr.length() >= length) {
            return indexStr;
        }

        StringBuilder sb = new StringBuilder();
        int prefixLength = 0;

        if (StringUtils.hasText(prefix)) {
            prefixLength = prefix.length();
            sb.append(prefix);
        }

        int paddingSize = length - prefixLength;

        if (paddingSize > 0) {
            sb.append(String.format("%0" + paddingSize + "d", index));
            return sb.toString();
        }

        return null;
    }

    /**
     * append with prefix
     */
    public static String appendWithPrefix(int length, String prefix, String obj) {
        if (Objects.isNull(obj)) {
            return null;
        }

        if (obj.length() >= length) {
            return obj;
        }

        StringBuilder sb = new StringBuilder();
        int prefixLength = 0;

        if (StringUtils.hasText(prefix)) {
            prefixLength = prefix.length();
            sb.append(prefix);
        }

        int paddingSize = length - (prefixLength + obj.length());

        if (paddingSize > 0) {
            sb.append(String.format("%0" + paddingSize + "d%s", 0, obj));
            return sb.toString();
        }

        return obj;
    }

    /**
     * truncate and convert object to string
     */
    public static String truncate(Object value, int maxSize) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }

        String valueStr = String.valueOf(value);

        int length = valueStr.length();
        if (length > maxSize) {
            valueStr = valueStr.substring(0, maxSize);
            log.warn("Event data for {} too long ({}) has been truncated to {}. Consider increasing column width.");
        }

        return valueStr;
    }

    /**
     * convert business type to tax rate
     */
    public static double toPersonalIncomeTax(String businessType) {
        if (BusinessType.PERSONAL.getValue().equals(businessType)) {
            return AppConstants.SALES_OUT_PERSONAL_INCOME_TAX;
        }

        return 0.0;
    }

    public static LocalTime toLocalTime(String value, String pattern) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(LocalDateUtil.UTC);
            return LocalTime.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at parsing string to time : {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * format date time with pattern
     *
     * @param time
     * @return
     */
    public static String localTimeToString(LocalTime time, String pattern) {
        if (time == null) {
            return "";
        }

        String formatPattern = PatternConstants.LOCAL_TIME_FORMAT;
        if (StringUtils.hasText(pattern)) {
            formatPattern = pattern;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPattern);
        return time.format(formatter);
    }

    /**
     * convertObjToMap
     *
     * @param
     * @param clazz
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map<String, Object> convertObjToMap(Object clazz) throws IllegalArgumentException, IllegalAccessException {
        var mapObj = new HashMap<String, Object>();

        Field[] allFields = clazz.getClass().getDeclaredFields();
        for (Field field : allFields) {
            field.setAccessible(true);
            Object value = field.get(clazz);
            mapObj.put(field.getName(), value);
        }
        return mapObj;
    }

    /**
     * convertObjToListMaps
     *
     * @param <T>
     * @param lstClazz
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<Map<String, Object>> convertObjToMap(List<T> lstClazz) throws IllegalArgumentException, IllegalAccessException {
        var lstResult = new ArrayList<Map<String, Object>>();
        for (Object clazz : lstClazz) {
            var mapObj = new HashMap<String, Object>();
            Field[] allFields = clazz.getClass().getDeclaredFields();
            for (Field field : allFields) {
                field.setAccessible(true);
                Object value = field.get(clazz);
                mapObj.put(field.getName(), value);
            }
            lstResult.add(mapObj);
        }
        return lstResult;
    }

    /**
     * distinctBy
     *
     * @param <T>
     * @param keyExtractor
     * @return
     */
    public static <T> Predicate<T> distinctBy(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * convert vendor
     */
    public static String toVendor(String type) {
        return mapVendor.get(type);
    }

    /**
     * convert SalesTeamChannel name
     */
    //    public static String toSalesTeamChannel(String type) {
    //        return mapSalesTeamChannel.get(type);
    //    }
    //
    //    public static String toSalesTeam(String type) {
    //        return mapSalesTeam.get(type);
    //    }
    //
    //    /**
    //     * get local using system
    //     */
    //    public static Locale toLocale() {
    //        return new Locale("vi", "VN");
    //    }
    //
    //    /**
    //     * Get week number of date
    //     */
    //    public static int toEvenWeek(LocalDate localDate) {
    //        TemporalField woy = WeekFields.of(toLocale()).weekOfWeekBasedYear();
    //        return ConvertUtil.toInteger(localDate.get(woy) % 2 == 0 ? WeekNoEnum.EVEN_WEEK.getValue() : WeekNoEnum.ODD_WEEK.getValue());
    //    }
    //
    //    public static String toAbbrWithFirstWords(String name, String delimiter) {
    //        if (!StringUtils.hasText(name)) return "";
    //
    //        String[] arr = name.split(delimiter);
    //
    //        StringBuilder stringBuilder = new StringBuilder();
    //        for (String str : arr) {
    //            stringBuilder.append(str.charAt(0));
    //        }
    //
    //        return stringBuilder.toString();
    //    }
    //
    //    public static Map<String, Integer> reactionToTags(String reactionIcon) {
    //        Map<String, Integer> result = new HashMap<>();
    //
    //        if (!StringUtils.hasText(reactionIcon)) return result;
    //
    //        for (String tag : reactionIcon.split(COMMA_SPLIT)) {
    //            String[] arr = tag.split(":");
    //            if (arr.length > 0) {
    //                result.put(arr[0], arr.length > 1 ? Integer.parseInt(arr[1]) : 1);
    //            }
    //        }
    //
    //        return result;
    //    }
    //
    //    public static String tagsToReaction(Map<String, Integer> tagMap) {
    //        if (ObjectUtils.isEmpty(tagMap)) {
    //            return "";
    //        }
    //
    //        StringBuilder stringBuilder = new StringBuilder();
    //        tagMap.forEach((k, v) -> {
    //            stringBuilder.append(k).append(TemplateConstants.CHAR_COLON).append(v).append(COMMA_SPLIT);
    //        });
    //
    //        return stringBuilder.substring(0, stringBuilder.length() - 1);
    //    }
    //
    //    public static String toCustomerAddress(List<TerritoryView> view, CustomerAddress address) {
    //        if (null == address || ObjectUtils.isEmpty(view)) {
    //            return "";
    //        }
    //
    //        if (StringUtils.hasText(address.getWardCode())) {
    //            TerritoryView terr = view.stream().filter(x -> x.getWardCode().equals(address.getWardCode())).findFirst().orElse(null);
    //
    //            if (null != terr) {
    //                return ConvertUtil.concatAddress(
    //                    address.getAddress(),
    //                    address.getAddress2(),
    //                    terr.getWardName(),
    //                    terr.getDistrictName(),
    //                    terr.getProvinceName()
    //                );
    //            }
    //        }
    //
    //        return ConvertUtil.concatAddress(address.getAddress(), address.getAddress2());
    //    }

    public static String toBusinessArea(String taxCode, String costCenter) {
        if (null != taxCode && mapUnibenTax.containsKey(taxCode)) return mapUnibenTax.get(taxCode);

        if (StringUtils.hasText(costCenter)) {
            int length = costCenter.length();
            String str = costCenter.substring(length - 1, length);

            if (str.equals("2")) {
                return "A200";
            } else {
                return "A100";
            }
        }

        return "";
    }

    private ConvertUtil() {}
}
