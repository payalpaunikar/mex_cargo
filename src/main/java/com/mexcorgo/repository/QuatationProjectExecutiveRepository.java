package com.mexcorgo.repository;


import com.mexcorgo.datamodel.QuatationProjectExecutive;
import com.mexcorgo.dto.response.ForwardedExecutiveDTO;
import com.mexcorgo.dto.response.PricingQuatation;
import com.mexcorgo.dto.response.ProjectQuatation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuatationProjectExecutiveRepository extends JpaRepository<QuatationProjectExecutive,Long> {

    @Query("SELECT new com.mexcorgo.dto.response.ForwardedExecutiveDTO(" +
            "pe.userId,pe.userName,pe.email,qpe.quatationForwardDate,qpe.quatationForwardTime)FROM QuatationProjectExecutive qpe JOIN qpe.projectExecutive pe " +
            "WHERE qpe.quatation.quatationId =:quatationId ")
    List<ForwardedExecutiveDTO> findByProjectExecutiveByQutationId(Long quatationId);

    @Query("SELECT new com.mexcorgo.dto.response.ProjectQuatation(" +
            "qpe.quatationProjectExecutiveId,l.leadId, l.leadReferenceNo, n.source, n.destination,"+
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation , n.movingDateAndTime," +
            "q.quatationRequiredDate, q.quatationRequiredTime," +
            "qpe.quatationForwardDate AS receivingQuatationDate," +
            "qpe.quatationForwardTime AS receivingQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus)" +
            "FROM QuatationProjectExecutive qpe "+
            "JOIN qpe.quatation q "+
            "JOIN q.lead l "+
            "JOIN l.need n "+
            "WHERE qpe.projectExecutive.userId = :projectExecutiveId"
    )
    List<ProjectQuatation> findQuatationDataByProjectExecutiveId(Long projectExecutiveId);

}
