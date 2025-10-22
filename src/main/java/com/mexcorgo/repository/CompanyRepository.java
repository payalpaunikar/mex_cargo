package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {

    Company findByLead_LeadId(Long leadId);
}
