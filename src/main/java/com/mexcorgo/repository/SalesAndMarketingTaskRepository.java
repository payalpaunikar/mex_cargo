package com.mexcorgo.repository;


import com.mexcorgo.datamodel.SalesAndMarketingTasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesAndMarketingTaskRepository extends JpaRepository<SalesAndMarketingTasks,Long> {
    List<SalesAndMarketingTasks> findByLead_LeadId(Long leadId);
}
