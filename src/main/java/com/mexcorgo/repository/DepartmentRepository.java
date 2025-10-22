package com.mexcorgo.repository;


import com.mexcorgo.datamodel.Department;
import com.mexcorgo.dto.response.DepartmentWithRoleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {

    @Query("Select d From Department d Join Fetch d.roles")
    List<Department> getAllDepartmentWithRoles();

    @Query("Select d From Department d Join Fetch d.roles where d.departmentId= :departmentId")
    Department getDepartmentWithRoles(@Param("departmentId")Long departmentId);
}
