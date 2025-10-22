package com.mexcorgo.repository;


import com.mexcorgo.datamodel.CompanyEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyEmployeeRepository extends JpaRepository<CompanyEmployee,Long> {

    CompanyEmployee findByLead_LeadId(Long leadId);
}
