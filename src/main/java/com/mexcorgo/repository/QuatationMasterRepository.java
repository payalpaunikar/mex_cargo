package com.mexcorgo.repository;


import com.mexcorgo.datamodel.QuatationMaster;
import com.mexcorgo.dto.response.MasterResponse;
import com.mexcorgo.dto.response.QuatationMasterWithMasterResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuatationMasterRepository extends JpaRepository<QuatationMaster,Long> {

       @Query("SELECT new com.mexcorgo.dto.response.MasterResponse(" +
               "qm.quatationMasterId,m.masterId,m.associateCode,m.serviceSector,m.companyName,m.contactName,m.contactNumber," +
               "m.emailId,m.grade,m.location,m.hub,m.state )" +
               "FROM QuatationMaster qm "+
               "JOIN qm.master m WHERE qm.quatation.quatationId=:quatationId")
       Optional<List<MasterResponse>> findMasterByQuatationId(Long quatationId);


}
