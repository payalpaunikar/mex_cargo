package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Need;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeedRepository extends JpaRepository<Need,Long> {

    Need findByLead_LeadId(Long leadId);
}
