package com.mexcorgo.repository;

import com.mexcorgo.datamodel.QuatationPlanningExecutive;
import com.mexcorgo.dto.response.PlanningEmpResponse;
import com.mexcorgo.dto.response.PlanningQuatation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuatationPlanningExecutiveRepository extends JpaRepository<QuatationPlanningExecutive,Long> {

    @Query("SELECT new com.mexcorgo.dto.response.PlanningEmpResponse(" +
            "u.userId, u.userName,u.email,u.mobileNumber) FROM QuatationPlanningExecutive qpe JOIN qpe.quatation  q JOIN q.lead l JOIN l.user u")
    List<PlanningEmpResponse> findPlanningExecutiveByQuatationId(Long quatationId);


    @Query("SELECT new com.mexcorgo.dto.response.PlanningQuatation(" +
            "l.leadId, l.leadReferenceNo, n.source, n.destination," +
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation, n.movingDateAndTime,q.quatationRequiredDate, q.quatationRequiredTime," +
            "q.quatationForwardedDateToPurchaseExecutive AS forwardQuatationDate,"+
            "q.quatationForwardedTimeToPurchaseExecutive AS forwardQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime) FROM QuatationPlanningExecutive qpe " +
            "JOIN qpe.quatation q " +
            "JOIN q.lead l " +
            "JOIN l.need n " +
            "WHERE qpe.planningExceutive.userId = :planningExecutiveId")
    List<PlanningQuatation> findQuatationWhichReceivePlanningExecutiveByExecutiveId(Long planningExecutiveId);
}
