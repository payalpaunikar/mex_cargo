package com.mexcorgo.repository;


import com.mexcorgo.datamodel.PhoneDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneDetailsRepository extends JpaRepository<PhoneDetails,Long> {

    List<PhoneDetails> findByToDo_ToDoId(Long toDoId);

}
