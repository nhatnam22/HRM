package com.module.hrm.web.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AwsDecompressResponse implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private AwsDecompressDetailResponse detail = new AwsDecompressDetailResponse();

    @JsonProperty("error")
    private String error;
}
