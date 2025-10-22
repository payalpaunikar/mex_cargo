package com.mexcorgo.repository;


import com.mexcorgo.datamodel.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp,Long> {
}
