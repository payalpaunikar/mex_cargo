package com.mexcorgo.repository;


import com.mexcorgo.datamodel.EmailDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailDetailsRepository extends JpaRepository<EmailDetails,Long> {

    List<EmailDetails> findByToDo_ToDoId(Long toDoId);
}
