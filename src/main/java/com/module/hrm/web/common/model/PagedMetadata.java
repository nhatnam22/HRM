package com.module.hrm.web.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedMetadata<T> implements Serializable {

    private int page;
    private int pageSize;
    private int lastPage;
    private int totalItems;
    private String sort;
    private String direction;

    @JsonIgnore
    private int actualSize;

    @JsonIgnore
    private List<T> body;

    public PagedMetadata() {}

    public PagedMetadata(RestListRequest req, Integer totalItems) {
        if (0 == req.getPageSize()) {
            // no pagination
            this.actualSize = totalItems;
        } else {
            this.actualSize = req.getPageSize();
        }

        this.page = req.getPage();
        this.pageSize = req.getPageSize();

        Double pages = Math.ceil(Double.valueOf(totalItems) / Double.valueOf(this.actualSize));
        this.lastPage = pages.intValue() == 0 ? 1 : pages.intValue();
        this.totalItems = totalItems;

        this.setSort(req.getSort());
        this.setDirection(req.getDirection());
    }
}
