package com.mexcorgo.repository;

import com.mexcorgo.datamodel.QuatationPricingExecutive;
import com.mexcorgo.dto.response.PricingQuatation;
import com.mexcorgo.dto.response.SalesQuatation;
import com.mexcorgo.dto.response.SendQuatationPricingEmpResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuatationPricingExecutiveRepository extends JpaRepository<QuatationPricingExecutive,Long> {

    @Query("SELECT new com.mexcorgo.dto.response.PricingQuatation(" +
            "l.leadId, l.leadReferenceNo, n.source, n.destination,"+
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation , n.movingDateAndTime," +
            "q.quatationRequiredDate, q.quatationRequiredTime," +
            "qpe.quatationForwardDate AS receivingQuatationDate," +
            "qpe.quatationForwardTime AS receivingQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus)" +
            "FROM QuatationPricingExecutive qpe "+
             "JOIN qpe.quatation q "+
             "JOIN q.lead l "+
            "JOIN l.need n "+
            "WHERE qpe.pricing_executive_id.userId = :pricingExecutiveId"
    )
    List<PricingQuatation> findQuatationDataByPricingExecutiveId(Long pricingExecutiveId);


    @Query("SELECT new com.mexcorgo.dto.response.SendQuatationPricingEmpResponse("+
            "pe.userId,pe.userName,pe.email,pe.mobileNumber," +
            "qpe.quatationForwardDate,qpe.quatationForwardTime)FROM QuatationPricingExecutive qpe " +
            "JOIN qpe.pricing_executive_id pe" +
            " WHERE qpe.quatation.quatationId = :quatationId")
    List<SendQuatationPricingEmpResponse> findPrincingEmpResponseByQuatationId(Long quatationId);


}
