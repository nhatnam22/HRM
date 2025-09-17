package com.module.hrm.web.common.repository;

import com.module.hrm.web.common.model.RestListRequest;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchRepository<T, M extends RestListRequest> {
    List<T> findByQuery(M request, int limit, int offset);

    Integer countByQuery(M request);
}
