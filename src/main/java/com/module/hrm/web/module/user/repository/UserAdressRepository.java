package com.module.hrm.web.module.user.repository;

import com.module.hrm.web.module.user.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAdressRepository extends JpaRepository<UserAddress, Long> {}
