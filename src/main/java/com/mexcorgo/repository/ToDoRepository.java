package com.mexcorgo.repository;


import com.mexcorgo.datamodel.ToDo;
import com.mexcorgo.dto.response.ToDoResponseInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {

    @Query("SELECT t.phoneDetailsCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getPhoneDetailsCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.emailDetailsCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getEmailDetailsCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.whatsAppDetailsCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getWhatsAppDetailsCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.PhysicalMeetingCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getPhysicalMeetingCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.NetSerchingCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getNetSerchingCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.meetingCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getMeetingCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.reportCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getReportCount(@Param("userId") Long userId, @Param("date") LocalDate date);

    @Query("SELECT t.miscellaneousCount FROM ToDo t WHERE t.user.userId = :userId AND t.toDoDate = :date")
    Integer getMiscellaneousCount(@Param("userId") Long userId, @Param("date") LocalDate date);


    Optional<ToDo> findByToDoDateAndUser_UserId(@Param("date")LocalDate date,@Param("userId")Long userId);


    @Query("SELECT t.toDoId as toDoId,t.toDoDate as toDoDate, t.phoneDetailsCount as phoneDetailsCount, " +
            "t.emailDetailsCount as emailDetailsCount, t.whatsAppDetailsCount as whatsAppDetailsCount," +
            "t.NetSerchingCount as netSerchingCount, t.PhysicalMeetingCount as physicalMeetingCount,t.reportCount as reportCount," +
            "t.meetingCount as meetingCount, t.miscellaneousCount as miscellaneousCount FROM ToDo t Where t.user.userId = :userId ORDER BY t.toDoDate DESC")
    Page<ToDoResponseInterface> getToDoEntityCountList(Long userId, Pageable pageable);
}
