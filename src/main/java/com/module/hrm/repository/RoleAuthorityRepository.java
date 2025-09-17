package com.module.hrm.repository;

import com.module.hrm.domain.RoleAuthority;
import com.module.hrm.domain.RoleAuthorityId;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface RoleAuthorityRepository extends JpaRepository<RoleAuthority, RoleAuthorityId>, JpaSpecificationExecutor<RoleAuthority> {
    @Query("SELECT DISTINCT ra.authority FROM RoleAuthority as ra WHERE ra.role.name IN :roles")
    Set<String> findDistinctByRoles(@Param("roles") Set<String> roles);

    @Query("SELECT DISTINCT ra.authority FROM RoleAuthority as ra WHERE ra.role.name = :roleName")
    Set<String> findDistinctByRoleName(@Param("roleName") String roleName);

    @Modifying
    @Query("DELETE FROM RoleAuthority as ra WHERE ra.role.name = :roleName")
    void deleteRoleAuthorityByRoleName(@Param("roleName") String roleName);

    List<RoleAuthority> findByRoleName(String roleName);
}
