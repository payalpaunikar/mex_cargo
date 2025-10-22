package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
    List<Meeting> findByToDo_ToDoId(Long toDoId);
}
