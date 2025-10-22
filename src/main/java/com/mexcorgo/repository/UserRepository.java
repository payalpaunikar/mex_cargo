package com.mexcorgo.repository;


import com.mexcorgo.datamodel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    User getByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u where u.role.roleName = :roleName And u.department.departmentName = :departmentName")
    Boolean headOfDepartmentExit(@Param("roleName")String roleName,@Param("departmentName")String departmentName);


    @Query("SELECT u FROM User u " +
            "JOIN u.role r " +
            "WHERE r.roleName = 'Member' " +
            "AND u.userId NOT IN (SELECT ug.userId FROM SalesAndMarketingGroup sg " +
            "JOIN sg.member ug)")
    List<User> findAvailableMembers();

    @Query("SELECT u FROM User u " +
            "JOIN u.role r " +
            "WHERE r.roleName = 'Leader' " +
            "AND u.userId NOT IN (SELECT ug.userId FROM SalesAndMarketingGroup sg " +
            "JOIN sg.leader ug)")
    List<User> findAvailableLeaders();


    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = 'Purchase Executive' ")
    List<User> findAllPurchaseExecutive();

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = 'Planning Executive' ")
    List<User> findAllPlanningExecutive();


    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = 'Pricing Executive' ")
    List<User> findAllPricingExecutive();

    @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = 'Project Executive' ")
    List<User> findAllProjectExecutive();


    @Query("SELECT u FROM User u JOIN u.role r JOIN u.department d WHERE r.roleName = 'Head' AND d.departmentName='Account' ")
    List<User> findAllAccountHead();

    @Query("SELECT u FROM User u WHERE u.userId IN :userIds")
    Set<User> findUsersByIds(@Param("userIds") Set<Long> userIds);

}
