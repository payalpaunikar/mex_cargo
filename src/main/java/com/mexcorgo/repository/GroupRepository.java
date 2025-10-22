package com.mexcorgo.repository;


import com.mexcorgo.datamodel.SalesAndMarketingGroup;
import com.mexcorgo.dto.response.GroupNameInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<SalesAndMarketingGroup,Long> {

      @Query("SELECT gr.groupId AS groupId,gr.groupName AS groupName FROM SalesAndMarketingGroup gr")
      List<GroupNameInterface> getGroupNames();


      @Query("SELECT gr.groupId AS groupId,gr.groupName AS groupName FROM SalesAndMarketingGroup gr WHERE gr.leader.userId = :leaderId")
      GroupNameInterface getLeaderGroupName(Long leaderId);

      @Query("SELECT gr.groupId AS groupId,gr.groupName AS groupName FROM SalesAndMarketingGroup gr WHERE gr.groupId =:groupId")
      GroupNameInterface getGroupNameByGroupId(Long groupId);


      @Modifying
      @Transactional
      @Query(value = "DELETE FROM group_member WHERE group_id = :groupId AND user_id = :memberId", nativeQuery = true)
      void removeMemberFromGroup(@Param("groupId") Long groupId, @Param("memberId") Long memberId);

      @Modifying
      @Transactional
      @Query("UPDATE SalesAndMarketingGroup gr SET gr.leader.id = :newLeaderId WHERE gr.groupId = :groupId")
      void replaceLeader(@Param("groupId") Long groupId, @Param("newLeaderId") Long newLeaderId);
}
