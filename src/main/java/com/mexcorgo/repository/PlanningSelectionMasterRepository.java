package com.mexcorgo.repository;


import com.mexcorgo.component.ServiceType;
import com.mexcorgo.datamodel.PlanningSelectionMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.mexcorgo.dto.response.QutationSelectedMasterForServiceResponse;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanningSelectionMasterRepository extends JpaRepository<PlanningSelectionMaster,Long> {

    @Query("SELECT new com.mexcorgo.dto.response.QutationSelectedMasterForServiceResponse(" +
            "psm.id, psm.serviceType,m.masterId,m.associateCode," +
            "m.serviceSector,m.companyName,m.contactName,m.contactNumber," +
            "m.emailId,m.grade,m.location,m.hub,m.state," +
            "psm.finalServiceAmount)FROM PlanningSelectionMaster psm JOIN psm.quatationMaster qm JOIN qm.master m")
    List<QutationSelectedMasterForServiceResponse> findByQuatationQuatationId(Long quatationId);

    Optional<PlanningSelectionMaster> findByQuatationQuatationIdAndServiceType(Long quatationId, ServiceType serviceTypeId);


    @Query("SELECT ps.serviceType FROM PlanningSelectionMaster ps WHERE ps.quatation.quatationId = :quotationId")
    List<ServiceType> findSelectedServiceTypesByQuotationId(Long quotationId);

}
