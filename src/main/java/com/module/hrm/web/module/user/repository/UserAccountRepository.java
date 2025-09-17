package com.module.hrm.web.module.user.repository;

import com.module.hrm.web.module.user.domain.UserAccount;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    Optional<UserAccount> findOneByDeleteFlagIsFalseAndUserCodeAndCompanyCode(String userCode, String companyCode);

    Optional<UserAccount> findOneByDeleteFlagIsFalseAndReferUserCodeAndCompanyCode(String referUserCode, String companyCode);

    Optional<UserAccount> findOneByDeleteFlagIsFalseAndUserId(Long id);

    @EntityGraph(attributePaths = "groups")
    Optional<UserAccount> findOneWithGroupByDeleteFlagIsFalseAndUserId(Long userId);

    @EntityGraph(attributePaths = "groups")
    Optional<UserAccount> findOneWithGroupByDeleteFlagIsFalseAndUserCodeAndCompanyCode(String userCode, String companyCode);

    @EntityGraph(attributePaths = "groups")
    List<UserAccount> findAllWithGroupByDeleteFlagIsFalseAndUserCodeInAndCompanyCode(Collection<String> userCode, String companyCode);

    @Query(
        "SELECT account FROM UserAccount AS account " +
        "   JOIN User user ON user.id = account.userId" +
        "    AND user.activated IS TRUE " +
        "   JOIN account.groups groups " +
        "   WHERE groups.code = :groupCode " +
        "       AND account.companyCode = :companyCode " +
        "       AND account.deleteFlag IS FALSE "
    )
    List<UserAccount> findAllWithGroupByDeleteFlagIsFalseAndGroupsCodeAndCompanyCode(
        @Param("groupCode") String groupCode,
        @Param("companyCode") String companyCode
    );

    @EntityGraph(attributePaths = "groups")
    List<UserAccount> findAllWithGroupByDeleteFlagIsFalseAndReferUserCodeInAndCompanyCode(
        Collection<String> referUserCodes,
        String companyCode
    );

    //    @Query(
    //        "SELECT COUNT(modify) " +
    //            " FROM UserAccount usr " +
    //            "  JOIN SalesForceUser sfu ON sfu.userCode = usr.userCode " +
    //            "  AND sfu.companyCode = usr.companyCode " +
    //            "  AND sfu.deleteFlag is FALSE " +
    //            "  JOIN SalesRoute route ON route.hierarchyCode = sfu.hierarchyCode " +
    //            "  AND route.companyCode = sfu.companyCode " +
    //            "  AND route.deleteFlag is FALSE " +
    //            "  LEFT JOIN CustomerModify modify ON modify.routeCode = route.routeCode " +
    //            "  AND modify.companyCode = route.companyCode " +
    //            "  AND modify.status IN :statuses " +
    //            "  AND modify.deleteFlag is FALSE " +
    //            " WHERE usr.deleteFlag = false " +
    //            "   AND sfu.fromDate <= :date " +
    //            "   AND (sfu.toDate >= :date OR sfu.toDate IS NULL) " +
    //            "   AND usr.userCode = :usmUserCode " +
    //            "   AND usr.companyCode = :companyCode "
    //    )
    //    long countCustomerModifyByUsmUserCode(
    //        @Param("usmUserCode") String usmUserCode,
    //        @Param("date") LocalDate date,
    //        @Param("statuses") CustomerModifyStatus[] statuses,
    //        @Param("companyCode") String companyCode
    //    );

    @Query(
        "SELECT account FROM UserAccount AS account " +
        "   JOIN User user ON user.id = account.userId" +
        "    AND user.activated IS TRUE " +
        "   JOIN account.groups groups " +
        "   WHERE groups.code = :roleSalesMan " +
        "       AND account.companyCode = :companyCode " +
        "       AND account.deleteFlag IS FALSE " +
        "       AND account.referUserCode IS NOT NULL " +
        //            "       AND account.userCode NOT IN " +
        //            "           (SELECT detail.userCode FROM SalesRouteDetail detail " +
        //            "            WHERE detail.deleteFlag IS FALSE " +
        //            "              AND detail.companyCode = account.companyCode " +
        //            "              AND (detail.toDate IS NULL OR detail.toDate >= :now)) " +
        " ORDER BY account.distributorCode, account.userCode "
    )
    @EntityGraph(attributePaths = { "groups" })
    List<UserAccount> findAllSalesManNotOnRoute(
        @Param("companyCode") String companyCode,
        @Param("roleSalesMan") String role,
        @Param("now") LocalDate now
    );

    @Query(
        "SELECT account FROM UserAccount AS account " +
        "   JOIN User user ON user.id = account.userId" +
        "    AND user.activated IS TRUE " +
        "   JOIN account.groups groups " +
        "   WHERE groups.code IN :usmRsmRoles " +
        "       AND account.companyCode = :companyCode " +
        "       AND account.deleteFlag IS FALSE " +
        "       AND account.referUserCode IS NOT NULL " +
        //            "       AND account.userCode NOT IN " +
        //            "           (SELECT detail.userCode FROM SalesForceUser detail " +
        //            "            WHERE detail.deleteFlag IS FALSE " +
        //            "              AND detail.companyCode = account.companyCode " +
        //            "              AND (detail.toDate IS NULL OR detail.toDate >= :now)) " +
        " ORDER BY account.userCode "
    )
    @EntityGraph(attributePaths = { "groups" })
    List<UserAccount> findAllUsmRsmNotOnHierarchy(
        @Param("companyCode") String companyCode,
        @Param("usmRsmRoles") Collection<String> usmRsmRoles,
        @Param("now") LocalDate now
    );

    @Query(
        "SELECT account FROM UserAccount AS account " +
        " JOIN account.groups AS groups ON groups.code =:roleSalesMan " +
        "   AND groups.deleteFlag IS FALSE " +
        "   AND groups.companyCode = :companyCode " +
        " WHERE account.deleteFlag IS FALSE" +
        "   AND account.distributorCode = :distributorCode " +
        "   AND account.companyCode = :companyCode "
    )
    List<UserAccount> findAllSalesMan(
        @Param("distributorCode") String distributorCode,
        @Param("roleSalesMan") String role,
        @Param("companyCode") String companyCode
    );
    //    @Query(
    //        "SELECT DISTINCT acc " +
    //            " FROM UserAccount acc  " +
    //            " JOIN acc.groups groups ON groups.companyCode = acc.companyCode " +
    //            "  AND groups.deleteFlag IS FALSE " +
    //            " JOIN User usr ON usr.id = acc.userId " +
    //            "  AND usr.activated = true " +
    //            " JOIN SalesForceUser usu ON usu.userCode = acc.userCode " +
    //            "  AND usu.fromDate <= :date " +
    //            "  AND (usu.toDate IS NULL OR usu.toDate >= :date) " +
    //            "  AND usu.deleteFlag = false " +
    //            "  AND usu.companyCode = acc.companyCode " +
    //            " WHERE acc.deleteFlag IS FALSE " +
    //            "   AND acc.companyCode = :companyCode " +
    //            " ORDER BY acc.userCode "
    //    )
    //    @EntityGraph(attributePaths = "groups")
    //    List<UserAccount> findAllUserAcountsOnHierarchy(@Param("date") LocalDate date, @Param("companyCode") String companyCode);
}
