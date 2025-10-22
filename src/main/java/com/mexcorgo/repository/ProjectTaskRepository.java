package com.mexcorgo.repository;


import com.mexcorgo.datamodel.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask,Long> {

    Optional<List<ProjectTask>> findByQuatationProjectExecutive_QuatationProjectExecutiveId(Long quatationProjectExceutiveId);


}
