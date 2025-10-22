package com.mexcorgo.repository;


import com.mexcorgo.datamodel.EndUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndUserDetailsRepository extends JpaRepository<EndUserDetails,Long> {

    EndUserDetails findByLeadLeadId(Long leadId);
}
