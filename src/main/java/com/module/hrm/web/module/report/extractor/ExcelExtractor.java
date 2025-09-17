package com.module.hrm.web.module.report.extractor;

import com.module.hrm.web.common.utils.MessageSourceResolver;
import java.io.ByteArrayOutputStream;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ExcelExtractor<T> {

    private MessageSourceResolver messageSourceReader;

    public abstract ByteArrayOutputStream extract(T data);
}
