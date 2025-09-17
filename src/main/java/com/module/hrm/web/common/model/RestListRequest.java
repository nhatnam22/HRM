package com.module.hrm.web.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.module.hrm.web.common.enumeration.DirectionOrder;
import com.module.hrm.web.common.enumeration.PatternConstants;
import com.module.hrm.web.common.utils.ConvertUtil;
import com.module.hrm.web.common.validator.EnumKeys;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class RestListRequest implements Serializable {

    public static final Integer MAX_PAGE_SIZE_DEFAULT = 500000;

    public static final Integer PAGE_SIZE_DEFAULT = 100;
    public static final String SORT_VALUE_DEFAULT = "id";
    public static final String DIRECTION_VALUE_DEFAULT = DirectionOrder.ASC.toString();

    @Size(max = PatternConstants.CODE_MAX_LENGTH)
    private String sort = SORT_VALUE_DEFAULT;

    @EnumKeys(value = DirectionOrder.class)
    private String direction = DIRECTION_VALUE_DEFAULT;

    @Min(1)
    private Integer page = 1;

    @Min(10)
    @Max(100)
    private Integer pageSize = PAGE_SIZE_DEFAULT; // TODO change 120 -> 100

    @Size(max = PatternConstants.NAME_MAX_LENGTH)
    private String query;

    public RestListRequest(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        if (page == null || page <= 0) {
            page = 1;
        }

        return page;
    }

    public int getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE_DEFAULT;
        }

        return pageSize;
    }

    @JsonIgnore
    public Integer getOffset() {
        return (getPage() - 1) * getPageSize();
    }

    @JsonIgnore
    public String getSortString() {
        String sortString = SORT_VALUE_DEFAULT;
        if (StringUtils.hasText(sort)) {
            sortString = sort;
        }

        if (StringUtils.hasText(direction) && direction.toUpperCase().equals(DirectionOrder.DESC.toString())) {
            return (ConvertUtil.camelCaseToLowerHyphen(sortString) + " " + DirectionOrder.DESC.toString());
        }

        return ConvertUtil.camelCaseToLowerHyphen(sortString) + " " + DIRECTION_VALUE_DEFAULT;
    }

    @JsonIgnore
    public boolean isEmptySort() {
        return !StringUtils.hasText(sort) || SORT_VALUE_DEFAULT.equals(sort);
    }

    @JsonIgnore
    public void clearSort() {
        sort = null;
    }
}
