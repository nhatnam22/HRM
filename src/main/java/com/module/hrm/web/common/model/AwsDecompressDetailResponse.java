package com.module.hrm.web.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AwsDecompressDetailResponse implements Serializable {

    @JsonProperty("decompress_id")
    private String decompressId;

    @JsonProperty("output_s3_key")
    private String output;

    @JsonProperty("files")
    private List<String> files;

    @JsonProperty("error")
    private Boolean error;
}
