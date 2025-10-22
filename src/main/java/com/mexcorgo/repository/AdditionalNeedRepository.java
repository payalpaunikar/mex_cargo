package com.mexcorgo.repository;


import com.mexcorgo.datamodel.AdditionalNeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalNeedRepository extends JpaRepository<AdditionalNeed,Long> {
}
