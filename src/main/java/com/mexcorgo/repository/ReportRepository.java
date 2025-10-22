package com.mexcorgo.repository;


import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {

    List<Report> findByToDo_ToDoId(Long toDoId);
}
