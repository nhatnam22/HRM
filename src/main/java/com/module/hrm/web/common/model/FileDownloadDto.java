package com.module.hrm.web.common.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

@Getter
@Setter
@AllArgsConstructor
public class FileDownloadDto implements Serializable {

    private InputStreamResource file;
    private String fileName;
    private long contentLength;
}
