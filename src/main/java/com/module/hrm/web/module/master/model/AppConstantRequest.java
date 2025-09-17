package com.module.hrm.web.module.master.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.module.hrm.web.common.utils.ClassUtil;
import com.module.hrm.web.common.utils.ConvertUtil;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppConstantRequest implements Serializable {

    @JsonIgnore
    private final Map<String, String> mapProperty = new HashMap<>();

    public void setFieldValue(String code, Object value, Object target) {
        String fieldName = mapProperty.get(code);
        ClassUtil.setValueToField(fieldName, value, target);
    }

    public String getFieldValue(String code, Object target) {
        String fieldName = mapProperty.get(code);
        Object value = ClassUtil.getValueFromField(fieldName, target.getClass(), target);
        return ConvertUtil.toString(value);
    }

    public boolean isContains(String key) {
        return mapProperty.containsKey(key);
    }

    protected void putProperty(String key, String property) {
        mapProperty.put(key, property);
    }

    @JsonIgnore
    public Collection<String> getPropertyFields() {
        return mapProperty.keySet();
    }
}
