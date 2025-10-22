package com.mexcorgo.repository;

import com.mexcorgo.datamodel.Lead;
import com.mexcorgo.dto.response.LeadResponseInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeadRepository extends JpaRepository<Lead,Long> {

    @Query("SELECT l.leadId AS leadId, l.leadReferenceNo AS leadReferenceNo, l.leadDate AS leadDate, " +
            "l.leadTime AS leadTime, l.wayOfLead AS wayOfLead, l.modeOfCommunication AS modeOfCommunication, " +
            "l.isQuatationCreated AS isQuatationCreated," +
            "l.isQuatationSendToUser As isQuatationSendToUser, n.followUpStatus AS followUpStatus,st.isCompleted AS isVerbalConfirmationTaskCompleted "+
            "FROM Lead l JOIN l.note n LEFT JOIN SalesAndMarketingTasks st ON st.lead.leadId = l.leadId AND st.name = 'Verbal Confirmation' " +
            "WHERE l.user.userId = :userId  ORDER BY l.leadDate DESC, l.leadId DESC")
    Page<LeadResponseInterface> findByUser_UserIdOrderByLeadDateDesc(Long userId, Pageable pageable);


    @Query("SELECT l.leadId AS leadId, l.leadReferenceNo AS leadReferenceNo, l.leadDate AS leadDate," +
            "l.leadTime AS leadTime, l.wayOfLead AS wayOfLead, l.modeOfCommunication AS modeOfCommunication "+
    "FROM Lead l WHERE l.leadId = :leadId")
    LeadResponseInterface findByLeadId(Long leadId);

//    @Query("SELECT l.leadReferenceNo FROM Lead l WHERE l.leadReferenceNo LIKE ?1% ORDER BY l.id DESC LIMIT 1")
//    String findLastLeadReference(String yearPrefix);

    @Query("SELECT l.leadReferenceNo FROM Lead l " +
            "WHERE l.user.id = :userId AND SUBSTRING(l.leadReferenceNo, 1, 2) = :year " +
            "ORDER BY l.leadReferenceNo DESC LIMIT 1")
    String findLastLeadReferenceForUser(@Param("userId") Long userId, @Param("year") String year);

    @EntityGraph(attributePaths = {"need"})
    Optional<Lead> findLeadWithNeedByLeadId(Long leadId);

    Lead findByNote_NoteId(Long noteId);


    @Query("SELECT l FROM Lead l JOIN l.note n JOIN n.followUps f WHERE f.followUpDate = :followUpDate")
    List<Lead> findLeadByFollowUpDate(@Param("followUpDate") LocalDate folloUpDate);


    @Query("SELECT l.leadReferenceNo FROM Lead l JOIN l.endUserDetails eu WHERE eu.userName = :userName")
    List<String> findLeadReferenceNoByEndUserName(@Param("userName")String userName);
}
