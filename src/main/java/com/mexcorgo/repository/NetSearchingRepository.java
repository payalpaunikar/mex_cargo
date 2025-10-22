package com.mexcorgo.repository;


import com.mexcorgo.datamodel.NetSearching;
import com.mexcorgo.datamodel.PhoneDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetSearchingRepository extends JpaRepository<NetSearching,Long> {

    List<NetSearching> findByToDo_ToDoId(Long toDoId);
}
