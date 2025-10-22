package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Miscellaneous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiscellaneousRepository extends JpaRepository<Miscellaneous,Long> {
    List<Miscellaneous> findByToDo_ToDoId(Long toDoId);
}
