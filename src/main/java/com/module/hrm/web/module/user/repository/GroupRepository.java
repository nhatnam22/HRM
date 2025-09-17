package com.module.hrm.web.module.user.repository;

import com.module.hrm.web.module.user.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {}
