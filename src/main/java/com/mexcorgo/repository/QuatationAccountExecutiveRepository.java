package com.mexcorgo.repository;


import com.mexcorgo.datamodel.QuatationAccountExecutive;
import com.mexcorgo.dto.response.AccountEmpResponse;
import com.mexcorgo.dto.response.ForwardedExecutiveDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuatationAccountExecutiveRepository extends JpaRepository<QuatationAccountExecutive,Long> {

    @Query("SELECT new com.mexcorgo.dto.response.AccountEmpResponse(" +
            "u.userId, u.userName,u.email,u.mobileNumber) FROM QuatationAccountExecutive qae JOIN qae.quatation  q JOIN q.lead l JOIN l.user u")
    List<AccountEmpResponse> findAccountEmployeesByQutationId(Long quatationId);

    Optional<QuatationAccountExecutive> findByQuatationQuatationId(Long quatationId);

    @Query("SELECT new com.mexcorgo.dto.response.ForwardedExecutiveDTO(" +
            "ae.userId, ae.userName, ae.email, qae.quatationForwardDate, qae.quatationForwardTime) " +
            "FROM QuatationAccountExecutive qae " +
            "JOIN qae.accountExecutive ae " +
            "WHERE qae.quatation.quatationId = :quotationId " +
            "AND qae.isServiceDataForwarded = true")
    List<ForwardedExecutiveDTO> getAllAccountExecutivesWithForwardedService(@Param("quotationId") Long quotationId);
}
