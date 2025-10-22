package com.mexcorgo.repository;

import com.mexcorgo.datamodel.MasterQuatationResponse;
import com.mexcorgo.dto.QuatationMasterPricingResponseSqlDto;
import com.mexcorgo.dto.request.MasterQuatationResponseDto;
import com.mexcorgo.dto.response.FinalQuatationPricingWithMasterDto;
import com.mexcorgo.dto.response.QuatationMasterWithMasterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterQuatationResponseRepository extends JpaRepository<MasterQuatationResponse,Long> {

        @Query("SELECT new com.mexcorgo.datamodel.MasterQuatationResponse(" +
                "mqr.masterQuatationResponseId," +
                "mqr.originalPackageCost,mqr.originalTrsCost,mqr.originalCarServiceCost," +
                "mqr.originalAdditionalServiceCost,mqr.finalPackageCost,mqr.finalTrsCost," +
                "mqr.finalCarServiceCost,mqr.finalAdditionalServiceCost,mqr.responseDate )" +
                "FROM MasterQuatationResponse mqr WHERE " +
                "mqr.quatationMaster.quatationMasterId =:quatationMasterId")
        MasterQuatationResponse findMasterQuatationResponseByQuatationMasterId(Long quatationMasterId);



        @Query("SELECT new com.mexcorgo.dto.QuatationMasterPricingResponseSqlDto(" +
                "l.leadId,l.leadReferenceNo,n.source,n.destination,n.commodity,n.size,n.weight," +
                "n.typeOfTransporatation,q.quatationId,n.movingDateAndTime,n.otherServices," +
                "n.carTransport,n.carMovingDate,n.carMovingTime," +
                "q.quatationRequiredDate,q.quatationRequiredTime,q.quatationReferenceNo," +
                "m.masterId,m.associateCode,m.serviceSector,m.companyName,m.contactName,m.contactNumber,m.emailId," +
                "m.grade,m.location,m.hub,m.state,mqr.originalPackageCost,mqr.originalTrsCost," +
                "mqr.originalCarServiceCost,mqr.originalAdditionalServiceCost AS originalAdditionalCost," +
                "mqr.finalPackageCost," +
                "mqr.finalTrsCost,mqr.finalCarServiceCost,mqr.finalAdditionalServiceCost AS finalAdditionalCost) " +
                "FROM MasterQuatationResponse mqr JOIN mqr.quatationMaster qm JOIN qm.master m " +
                "JOIN qm.quatation q JOIN q.lead l JOIN l.need n WHERE q.quatationId = :quatationId")
        List<QuatationMasterPricingResponseSqlDto> findQuatationAndMasterPricingByQuatationId(Long quatationId);



        @Query("SELECT new com.mexcorgo.dto.response.QuatationMasterWithMasterResponse(" +
                "m.masterId,m.associateCode,m.serviceSector,m.companyName," +
                "m.contactName,m.contactNumber,m.emailId,m.grade,m.location," +
                "m.hub,m.state,mqr.originalPackageCost,mqr.originalTrsCost," +
                "mqr.originalCarServiceCost,mqr.originalAdditionalServiceCost," +
                "mqr.finalPackageCost,mqr.finalTrsCost,mqr.finalCarServiceCost," +
                "mqr.finalAdditionalServiceCost,qm.quatationMasterId)FROM MasterQuatationResponse mqr " +
                "JOIN mqr.quatationMaster qm JOIN qm.master m " +
                "WHERE qm.quatation.quatationId = :quatationId")
        List<QuatationMasterWithMasterResponse> findQuatationMastersWithMasterResponseByQuatationId(Long quatationId);


}
