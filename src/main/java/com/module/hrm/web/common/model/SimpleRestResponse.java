package com.module.hrm.web.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class SimpleRestResponse<T> extends RestResponse<T> {

    public SimpleRestResponse(T payload) {
        super(payload);
    }

    public SimpleRestResponse(T payload, HttpStatus status) {
        super(payload, status);
    }
}
