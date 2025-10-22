package com.mexcorgo.repository;


import com.mexcorgo.datamodel.NeedExtraDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NeedExtraDetailsRepository extends JpaRepository<NeedExtraDetails,Long> {
    Optional<NeedExtraDetails> findByLeadLeadId(Long leadId);
}
