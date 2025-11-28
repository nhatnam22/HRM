package com.module.hrm.web.common.utils;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;

@Slf4j
public class ClassUtil {

    /**
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Field getFieldClass(String fieldName, Class<?> clazz) {
        try {
            Field field = FieldUtils.getDeclaredField(clazz, fieldName, true);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at getFieldClass: {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param fieldName name field
     * @param target    Object contains data
     * @param value     value
     */
    public static void setValueToField(String fieldName, Object value, Object target) {
        try {
            Field field = getFieldClass(fieldName, target.getClass());
            field.set(target, parseValueFieldType(value, field));
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at setValueToField: {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param fieldName
     * @param clazz
     * @param target Object contains data
     * @return
     */
    public static Object getValueFromField(String fieldName, Class<?> clazz, Object target) {
        try {
            Field field = getFieldClass(fieldName, clazz);
            return field.get(target);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("Exception at getValueFromField: {}", e.getClass().getSimpleName());
            throw new RuntimeException(e.getMessage());
        }
    }

    // value là dữ liệu string trong file excel còn field sẽ có kiểu dữ liệu đã định nghĩa trong Dto
    private static Object parseValueFieldType(Object value, Field field) {
        if (field.getType().equals(Character.class)) {
            return ConvertUtil.toString(value).charAt(0);
        }
        if (field.getType().equals(Short.class)) {
            return Short.parseShort(ConvertUtil.toString(value));
        }
        if (field.getType().equals(Integer.class)) {
            return ConvertUtil.toInteger(value);
        }
        if (field.getType().equals(Long.class)) {
            return ConvertUtil.toLong(value);
        }
        if (field.getType().equals(Float.class)) {
            return Float.parseFloat(ConvertUtil.toString(value));
        }
        if (field.getType().equals(Double.class)) {
            return ConvertUtil.toDoubleOrZero(value);
        }
        if (field.getType().equals(Byte.class)) {
            return Byte.parseByte(ConvertUtil.toString(value));
        }
        if (field.getType().equals(Boolean.class)) {
            return ConvertUtil.toBoolean(value);
        }
        return value;
    }

    private ClassUtil() {}
}
