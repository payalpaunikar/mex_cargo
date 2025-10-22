package com.mexcorgo.service;


import com.mexcorgo.datamodel.SalesAndMarketingGroup;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.AddMemberRequest;
import com.mexcorgo.dto.request.GroupRequest;
import com.mexcorgo.dto.request.MemberRequest;
import com.mexcorgo.dto.response.GroupNameInterface;
import com.mexcorgo.dto.response.GroupNameResponse;
import com.mexcorgo.dto.response.GroupResponse;
import com.mexcorgo.dto.response.GroupTeamEntityResponse;
import com.mexcorgo.exception.InvalidMemberSizeException;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UnauthorizedException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.GroupRepository;
import com.mexcorgo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {

    private UserRepository userRepository;

    private GroupRepository groupRepository;

    Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    public GroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }


      public GroupResponse createdGroup(GroupRequest groupRequest){
         if (groupRequest.getMembers().size()<2){
            throw new InvalidMemberSizeException("In the group at least 2 member we needed");
         }

         User leader = userRepository.findById(groupRequest.getLeaderId())
                 .orElseThrow(()-> new UserNotFoundException("User not found with id : "+groupRequest.getLeaderId()));

         Set<Long> memberIdInLong = groupRequest.getMembers().stream()
                 .map(MemberRequest::getMemberId)
                 .collect(Collectors.toSet());

          Set<User> memberList = userRepository.findUsersByIds(memberIdInLong);

          SalesAndMarketingGroup salesAndMarketingGroup = new SalesAndMarketingGroup();
         salesAndMarketingGroup.setGroupName(groupRequest.getGroupName());
         salesAndMarketingGroup.setLeader(leader);
         salesAndMarketingGroup.setMember(memberList);

       SalesAndMarketingGroup saveGroup = groupRepository.save(salesAndMarketingGroup);

       GroupResponse groupResponse = new GroupResponse();
         groupResponse = convertGroupIntoGroupResponse(saveGroup,groupResponse);

         return groupResponse;
      }


      public GroupResponse getGroupById(Long groupId){
        SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
                .orElseThrow(()-> new UserNotFoundException("Group with id : "+groupId+" is not found"));

        GroupResponse groupResponse = new GroupResponse();
        groupResponse = convertGroupIntoGroupResponse(existingGroup,groupResponse);

        return groupResponse;
      }


      public Boolean deleteGroup(Long groupId){
        SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
                .orElseThrow(()-> new UserNotFoundException("Group with id : "+groupId+" is not found"));

        groupRepository.deleteById(groupId);

        return true;
      }

      public GroupNameResponse getGroupNameById(Long groupId){
        GroupNameInterface groupNameInterface = groupRepository.getGroupNameByGroupId(groupId);

        GroupNameResponse groupNameResponse = new GroupNameResponse();
        groupNameResponse.setGroupId(groupNameInterface.getGroupId());
        groupNameResponse.setGroupName(groupNameInterface.getGroupName());

        return groupNameResponse;
      }


      private GroupResponse convertGroupIntoGroupResponse(SalesAndMarketingGroup group,GroupResponse groupResponse){
        groupResponse.setGroupId(group.getGroupId());
        groupResponse.setGroupName(group.getGroupName());

          GroupTeamEntityResponse groupTeamEntityResponse = new GroupTeamEntityResponse();
          User leader = group.getLeader();
          groupTeamEntityResponse.setId(leader.getUserId());
          groupTeamEntityResponse.setName(leader.getUserName());
          groupTeamEntityResponse.setEmail(leader.getEmail());
          groupTeamEntityResponse.setNumber(leader.getMobileNumber());
          groupTeamEntityResponse.setRole(leader.getRole().getRoleName());

          groupResponse.setLeader(groupTeamEntityResponse);

          Set<GroupTeamEntityResponse> memberList = group.getMember()
                  .stream()
                  .map(member-> {
                      GroupTeamEntityResponse groupTeamEntityResponse1 = new GroupTeamEntityResponse();
                      groupTeamEntityResponse1.setId(member.getUserId());
                      groupTeamEntityResponse1.setName(member.getUserName());
                      groupTeamEntityResponse1.setNumber(member.getMobileNumber());
                      groupTeamEntityResponse1.setEmail(member.getEmail());
                      groupTeamEntityResponse1.setRole(member.getRole().getRoleName());

                      return groupTeamEntityResponse1;
                  }).collect(Collectors.toSet());

          groupResponse.setMemberList(memberList);

          return groupResponse;
      }


      public List<GroupNameResponse> getGroupsName(User user){
         
        if (user.getRole().getRoleName().equalsIgnoreCase("Admin") || 
        user.getRole().getRoleName().equalsIgnoreCase("Head")){
           List<GroupNameInterface> groupNameResponseInterfaceList = groupRepository.getGroupNames();

           List<GroupNameResponse> groupNameResponseList = groupNameResponseInterfaceList.stream()
                   .map(groupNameInterface -> {
                       logger.info("groupId : "+groupNameInterface.getGroupId());
                       logger.info("groupName: "+groupNameInterface.getGroupName());
                       GroupNameResponse groupNameResponse = new GroupNameResponse();
                       groupNameResponse.setGroupId(groupNameInterface.getGroupId());
                       groupNameResponse.setGroupName(groupNameInterface.getGroupName());

                       return groupNameResponse;
                   }).toList();

           return groupNameResponseList;

        } else if (user.getRole().getRoleName().equalsIgnoreCase("Leader")) {

            GroupNameInterface leaderGroupNameInterface = groupRepository.getLeaderGroupName(user.getUserId());


            GroupNameResponse groupNameResponse = new GroupNameResponse();
            groupNameResponse.setGroupId(leaderGroupNameInterface.getGroupId());
            groupNameResponse.setGroupName(leaderGroupNameInterface.getGroupName());

            return List.of(groupNameResponse);
        }

          throw new UnauthorizedException("You don't have permision to access the group name");

      }


     public GroupNameResponse updateGroupName(Long groupId,String groupName){
        SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
                .orElseThrow(()-> new UserNotFoundException("group with id : "+groupId+" is not found"));

        existingGroup.setGroupName(groupName);

        SalesAndMarketingGroup saveGroup = groupRepository.save(existingGroup);

        GroupNameResponse groupNameResponse = new GroupNameResponse();
        groupNameResponse.setGroupId(saveGroup.getGroupId());
        groupNameResponse.setGroupName(saveGroup.getGroupName());

        return groupNameResponse;
     }


     //If In the Group we have only 2 member We can not delete it.but if group contain more than 2 member then group member can be detelte
     public String deleteGroupMemberFromGroup(Long groupId, Long memberId){
        User existingUser = userRepository.findById(memberId)
                .orElseThrow(()-> new UserNotFoundException("User with id : "+memberId+" is not found"));

        SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
                .orElseThrow(()-> new UserNotFoundException("Group with id : "+groupId+" is not found"));

        if(existingUser.getRole().getRoleName().equalsIgnoreCase("Member")){
            if (existingGroup.getMember().size() > 2){
                 groupRepository.removeMemberFromGroup(groupId,memberId);
                 return "Group Member deleted succefully.";
            }
            return "In Group we have only 2 member, so we cann't deleted member.because in the group 2 member is mandotory.";
        }

        throw new UnauthorizedException("In the group we only delete the member");
     }


     public String updateGroupLeaderFromGroup(Long groupId,Long leaderId){
        User existingUser = userRepository.findById(leaderId)
                .orElseThrow(()-> new UserNotFoundException("User with id : "+leaderId+" is not found"));

         SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
                 .orElseThrow(()-> new UserNotFoundException("Group with id : "+groupId+" is not found"));

         if (existingUser.getRole().getRoleName().equalsIgnoreCase("Leader")){
             groupRepository.replaceLeader(groupId,leaderId);

             return "Group Leader update succefully.";
         }

         throw new UnauthorizedException("In the group we only update leader");

     }

    public GroupResponse addGroupMemberInExistingGroup(Long groupId, AddMemberRequest addMemberRequest){

       Set<User> existingMembers = userRepository.findUsersByIds(addMemberRequest.getMemberIds());

       SalesAndMarketingGroup existingGroup = groupRepository.findById(groupId)
               .orElseThrow(()-> new ResourceNotFoundException("group with id : "+groupId+" is not found"));

       Set<User> groupMemberList = existingGroup.getMember();

       groupMemberList.addAll(existingMembers);

      existingGroup.setMember(groupMemberList);

     SalesAndMarketingGroup saveGroup =  groupRepository.save(existingGroup);

     GroupResponse groupResponse = new GroupResponse();
     groupResponse = convertGroupIntoGroupResponse(saveGroup,groupResponse);

     return groupResponse;
    }
}
