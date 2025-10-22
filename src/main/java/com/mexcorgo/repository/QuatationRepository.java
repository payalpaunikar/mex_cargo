package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Quatation;
import com.mexcorgo.dto.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuatationRepository extends JpaRepository<Quatation,Long> {


    @Query("SELECT new com.mexcorgo.dto.response.SalesQuatation( " +
            "l.leadId, l.leadReferenceNo, n.source, n.destination, " +  // Fixed field names
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation ,n.movingDateAndTime,q.quatationRequiredDate, q.quatationRequiredTime," +
            "q.quatationForwardedDateToPurchaseExecutive AS forwardQuatationDate," +
            " q.quatationForwardedTimeToPurchaseExecutive AS forwardQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus,q.isParticularAmountAdded) " +
            "FROM Quatation q " +
            "JOIN q.lead l " +
            "JOIN l.need n " +  // Fixed alias reference
            "WHERE l.user.userId = :userId")
    List<SalesQuatation> findQuatationDataByUserId(@Param("userId") Long userId);



    @Query("SELECT new com.mexcorgo.dto.response.PurchaseEmpResponse(" +
            "u.userId, u.userName, u.email, u.mobileNumber) " +
            "FROM Quatation q JOIN q.referredToPurchaseExecutives u " +
            "WHERE q.quatationId = :quatationId")
    List<PurchaseEmpResponse> findPurchaseExecutivesByQuatationId(Long quatationId);


    @Query("SELECT new com.mexcorgo.dto.response.PurchaseQuatation( " +
            "l.leadId, l.leadReferenceNo, n.source, n.destination, " +  // Fixed field names
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation , n.movingDateAndTime,q.quatationRequiredDate, q.quatationRequiredTime, " +
            "q.quatationForwardedDateToPurchaseExecutive AS receivingQuatationDate, " +
            "q.quatationForwardedTimeToPurchaseExecutive AS receivingQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus) " +
            "FROM Quatation q " +
            "JOIN q.referredToPurchaseExecutives p "+
            "JOIN q.lead l " +
            "JOIN l.need n " +  // Fixed alias reference
            "WHERE p.userId = :purchaseExecutiveId")
    List<PurchaseQuatation> findQuatationDataByPurchaseExecutiveId(Long purchaseExecutiveId);




    @Query("SELECT new com.mexcorgo.dto.response.PurchaseQuatation( " +
            "l.leadId, l.leadReferenceNo, n.source, n.destination, " +  // Fixed field names
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation , n.movingDateAndTime,q.quatationRequiredDate, q.quatationRequiredTime, " +
            "q.quatationForwardedDateToPurchaseExecutive AS receivingQuatationDate, " +
            "q.quatationForwardedTimeToPurchaseExecutive AS receivingQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus) " +
            "FROM Quatation q " +
            "JOIN q.referredToPurchaseExecutives p "+
            "JOIN q.lead l " +
            "JOIN l.need n " +  // Fixed alias reference
            "WHERE p.userId = :purchaseExecutiveId AND q.quatationStatus='SENT_TO_PRICING_EXECUTIVE' ")
    List<PurchaseQuatation> findQuatationDataWhichWeSendToPricingExecutiveByPurchaseExecutiveId(Long purchaseExecutiveId);

    Quatation findByLead_LeadId(Long leadId);


    @Query("SELECT new com.mexcorgo.dto.response.EmailReceiverInfo(u.email,u.userName AS name)" +
            " FROM Quatation q JOIN q.lead l JOIN l.user u WHERE q.quatationId = :quatationId")
    EmailReceiverInfo findQuatationCreatorEmailByQuotationId(Long quatationId);


    @Query("SELECT new com.mexcorgo.dto.response.SalesQuatation(" +
            "l.leadId, l.leadReferenceNo, n.source, n.destination, " +  // Fixed field names
            "q.quatationId,n.commodity,n.size,n.weight,n.typeOfTransporatation, n.movingDateAndTime,q.quatationRequiredDate, q.quatationRequiredTime, " +
            "q.quatationForwardedDateToPurchaseExecutive AS forwardQuatationDate," +
            " q.quatationForwardedTimeToPurchaseExecutive AS forwardQuationTime, q.quatationReferenceNo," +
            "n.otherServices,n.carTransport,n.carMovingDate,n.carMovingTime,q.quatationStatus,q.isParticularAmountAdded) " +
            "FROM Quatation q " +
            "JOIN q.lead l " +
            "JOIN l.need n " +  // Fixed alias reference
            "WHERE l.user.userId = :userId AND q.quatationStatus='PRICING_FINALIZED_AND_SENT_TO_SALES' ")
    List<SalesQuatation> findQuatationWhichPricingIsFinalized(Long userId);


//    @Query("SELECT new com.mexcorgo.dto.response.FinalQuatationPricingWithMasterDto(" +
//            "m.masterId, m.associateCode, m.serviceSector, m.companyName, " +
//            "m.contactName, m.contactNumber, m.emailId, m.grade, m.location, " +
//            "m.hub, m.state, " +
//            "mqr.originalPackageCost, mqr.originalTrsCost, mqr.originalCarServiceCost, mqr.originalAdditionalServiceCost, " +
//            "mqr.finalPackageCost, mqr.finalTrsCost, mqr.finalCarServiceCost, mqr.finalAdditionalServiceCost, " +
//            "q.companyPackageCost, q.companyTrsCost, q.companyCarServiceCost, q.companyAdditionalServiceCost) " +
//            "FROM Quatation q " +
//            "JOIN q.finalizedMasterResponse qm JOIN MasterQuatationResponse mqr on mqr.quatationMaster.quatationMasterId = qm.quatationMasterId" +  // Get the finalized response
//            "JOIN qm.master m " +
//            "WHERE q.quatationId = :quatationId")
//    Optional<FinalQuatationPricingWithMasterDto> findFinalQuatationPricingWithMasterByQuatationId(@Param("quatationId") Long quatationId);




}
