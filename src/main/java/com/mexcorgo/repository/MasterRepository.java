package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master,Long> {
}
