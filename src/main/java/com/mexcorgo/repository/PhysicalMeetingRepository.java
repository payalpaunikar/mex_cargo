package com.mexcorgo.repository;


import com.mexcorgo.datamodel.PhysicalMeeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PhysicalMeetingRepository extends JpaRepository<PhysicalMeeting,Long> {

    List<PhysicalMeeting> findByToDo_ToDoId(Long toDoId);
}
