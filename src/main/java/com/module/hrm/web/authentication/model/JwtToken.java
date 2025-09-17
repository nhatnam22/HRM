package com.module.hrm.web.authentication.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class JwtToken {

    private String idToken;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String androidUsmVersion;

    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String iosUsmVersion;

    @Getter
    @Setter
    private Boolean needChangePwd;

    @Getter
    @Setter
    private String groupCode;

    @Getter
    @Setter
    private String fullName;

    @Getter
    @Setter
    private String distributorCode;

    @Getter
    @Setter
    private Integer distance = 200;

    public JwtToken(String idToken, String fullName, Boolean needChangePwd, String groupCode, String distributorCode) {
        this.idToken = idToken;
        this.fullName = fullName;
        this.needChangePwd = needChangePwd;
        this.groupCode = groupCode;
        this.distributorCode = distributorCode;
    }

    public JwtToken(
        String idToken,
        String fullName,
        Boolean needChangePwd,
        String groupCode,
        String distributorCode,
        String androidVersion,
        String iosVersion
    ) {
        this.idToken = idToken;
        this.fullName = fullName;
        this.needChangePwd = needChangePwd;
        this.groupCode = groupCode;
        this.distributorCode = distributorCode;
        this.androidUsmVersion = androidVersion;
        this.iosUsmVersion = iosVersion;
    }

    @JsonProperty("id_token")
    String getIdToken() {
        return idToken;
    }

    void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
