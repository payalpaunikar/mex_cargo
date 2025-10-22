package com.mexcorgo.repository;

import com.mexcorgo.datamodel.QuatationParticularsAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuatationParticularsAmountRepository extends JpaRepository<QuatationParticularsAmount,Long> {

    QuatationParticularsAmount findByQuatation_QuatationId(Long quatationId);

}
