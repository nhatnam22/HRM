package com.module.hrm.repository;

import com.module.hrm.domain.User;
import jakarta.persistence.Tuple;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesByLogin(String login);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneWithRolesById(Long id);

    Optional<User> findOneByEmailIgnoreCase(String email);

    List<User> findAllByIdInAndActivatedIsTrue(Collection<Long> userIds);

    @Query(
        "SELECT DISTINCT " +
        //            "  usu.hierarchyCode, " +
        "  acc.userCode, " +
        "  usr.email, " +
        "  usr.langKey, " +
        "  acc.fullName " +
        " FROM User usr " +
        " INNER JOIN UserAccount acc ON usr.id = acc.userId " +
        "  AND acc.deleteFlag = false " +
        //            " INNER JOIN SalesForceUser usu ON usu.userCode = acc.userCode " +
        //            "  AND usu.fromDate <= :date " +
        //            "  AND (usu.toDate IS NULL OR usu.toDate >= :date) " +
        //            "  AND usu.deleteFlag = false " +
        //            "  AND usu.companyCode = acc.companyCode " +
        " WHERE usr.activated = true " +
        "   AND acc.companyCode = :companyCode " +
        //            "   AND usu.hierarchyCode IN (:hierarchyCodes) " +
        " ORDER BY acc.userCode "
    )
    List<Tuple> findAllByHierarchyCodes(
        @Param("hierarchyCodes") Set<String> hierarchyCodes,
        @Param("date") LocalDate date,
        @Param("companyCode") String companyCode
    );

    @Query(
        value = "SELECT DISTINCT ON (usrDistributor.distributor_code, usrDistributor.user_code) " +
        "    usrDistributor.distributor_code, " +
        "    usrDistributor.user_code, " +
        "    usr.email, " +
        "    usr.lang_key " +
        " FROM jhi_user usr " +
        " INNER JOIN vmu_user_distributor usrDistributor" +
        "    ON usr.id = usrDistributor.user_id " +
        "    AND usrDistributor.from_date <= :date " +
        "    AND (usrDistributor.to_date IS NULL OR usrDistributor.to_date >= :date) " +
        "    AND usrDistributor.delete_flag = false " +
        "    AND usrDistributor.company_code = :companyCode " +
        " INNER JOIN jhi_user_role roles " +
        "    ON roles.user_id = usr.id " +
        " WHERE usr.activated = true " +
        "   AND usrDistributor.distributor_code IN :distributorCodes " +
        "   AND roles.role_name = :roleName " +
        " ORDER BY usrDistributor.distributor_code ",
        nativeQuery = true
    )
    List<Tuple> findAllSalesOperationUser(
        @Param("distributorCodes") List<String> distributorCodes,
        @Param("roleName") String roleName,
        @Param("date") LocalDate date,
        @Param("companyCode") String companyCode
    );

    @Query(
        value = "SELECT " +
        " usr.email, " +
        " usr.lang_key " +
        " FROM jhi_user usr" +
        " INNER JOIN jhi_user_role rle " +
        " ON rle.user_id = usr.id " +
        " WHERE rle.role_name = :roleName ",
        nativeQuery = true
    )
    List<Tuple> findAllUserByRole(@Param("roleName") String roleName);

    @Query(
        value = "SELECT " +
        " usr.email, " +
        " usr.lang_key " +
        " FROM jhi_user usr " +
        " INNER JOIN jhi_user_role rle " +
        " ON rle.user_id = usr.id " +
        " WHERE rle.role_name = :roleName " +
        " AND usr.activated = true ",
        nativeQuery = true
    )
    List<Tuple> findAllUserActivedByRole(@Param("roleName") String roleName);
}
