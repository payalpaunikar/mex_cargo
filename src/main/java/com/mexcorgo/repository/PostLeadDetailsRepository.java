package com.mexcorgo.repository;


import com.mexcorgo.datamodel.PostLeadDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLeadDetailsRepository extends JpaRepository<PostLeadDetails,Long> {
}
