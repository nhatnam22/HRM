package com.module.hrm.web.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileUploadDto<T extends AbstractRecordDto> implements Serializable {

    private int total;
    private int successTotal;
    private int failureTotal;
    private String uploadedPath;

    @JsonIgnore
    private List<T> errorRecords = new ArrayList<T>();

    public FileUploadDto(int total, int successTotal, int failureTotal, List<T> errorRecords) {
        this.total = total;
        this.successTotal = successTotal;
        this.failureTotal = failureTotal;
        this.errorRecords = errorRecords;
    }

    @JsonIgnore
    public Map<Integer, T> getMapErrorRecords() {
        return this.errorRecords.stream().collect(Collectors.toMap(AbstractRecordDto::getLineNumber, Function.identity()));
    }
}
