package com.module.hrm.web.common.model;

import com.module.hrm.web.common.model.PagedMetadata;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagedRestResponse<T> extends RestResponse<List<T>> {

    private PagedMetadata<T> metaData;

    public PagedRestResponse(PagedMetadata<T> metaData) {
        super(metaData.getBody());
        this.metaData = metaData;
    }
}
