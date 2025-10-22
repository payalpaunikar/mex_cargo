package com.mexcorgo.repository;


import com.mexcorgo.datamodel.WhatsAppDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhatsAppDetailsRepository extends JpaRepository<WhatsAppDetails,Long> {

    List<WhatsAppDetails> findByToDo_ToDoId(Long toDoId);
}
