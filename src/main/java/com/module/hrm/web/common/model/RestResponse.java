package com.module.hrm.web.common.model;

import com.module.hrm.web.common.enumeration.MessageKeys;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public abstract class RestResponse<T> {

    private T payload;

    private int status;

    private String message;

    public RestResponse(T payload) {
        this.payload = payload;
        this.status = HttpStatus.OK.value();
        this.message = MessageKeys.I_SUCCESS.getValue();
    }

    public RestResponse(T payload, HttpStatus status) {
        this.payload = payload;
        this.status = status.value();
        //this.message = MessageKeys.I_SUCCESS.getValue();
    }
}
