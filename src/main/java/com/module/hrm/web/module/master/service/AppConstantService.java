package com.module.hrm.web.module.master.service;

import com.module.hrm.web.common.enumeration.CommonCode;
import com.module.hrm.web.module.master.model.AppConstantRequest;
import java.time.LocalDate;

public interface AppConstantService {
    <T extends AppConstantRequest> T findAll(Object Target, Class<T> valueType);

    <T extends AppConstantRequest> T updateConstant(T request);

    LocalDate[] getWorkingDate(LocalDate day);

    String getValueConstant(CommonCode commonCode, String typeCode);

    Integer getMaxRemoteVisitNumber(String salesTeamCode);
    String getPasswordInit(String groupCode);

    Integer getMaxCoachingNonWorkWithInMonth();
}
