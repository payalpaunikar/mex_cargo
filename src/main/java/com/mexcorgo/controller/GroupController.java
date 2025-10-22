package com.mexcorgo.controller;



import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.AddMemberRequest;
import com.mexcorgo.dto.request.GroupRequest;
import com.mexcorgo.dto.response.GroupNameResponse;
import com.mexcorgo.dto.response.GroupResponse;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

       private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/created/group")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<GroupResponse> createdGroup(@RequestBody GroupRequest groupRequest){
      return ResponseEntity.ok(groupService.createdGroup(groupRequest));
    }


    @GetMapping("/get/groups/name")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader')")
    public ResponseEntity<List<GroupNameResponse>> getGroupName(Authentication authentication){
       User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(groupService.getGroupsName(currentUser));
    }

    @GetMapping("/get/group/{groupId}")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader')")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable("groupId")Long groupId){
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

    @DeleteMapping("/delete/group/{groupId}")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("groupId")Long groupId){
        return ResponseEntity.ok(groupService.deleteGroup(groupId));
    }

    @PatchMapping("/update/group/{groupId}/name")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public GroupNameResponse updateGroupName(@PathVariable("groupId")Long groupId,@RequestParam("groupName")String groupName){
      return groupService.updateGroupName(groupId,groupName);
    }


    @GetMapping("/get/group/{groupId}/name")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public GroupNameResponse getGroupNameById(@PathVariable("groupId")Long groupId){
        return groupService.getGroupNameById(groupId);
    }


    @DeleteMapping("/delete/group/{groupId}/member/{memberId}")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<String> deleteGroupMemberFromGroup(@PathVariable("groupId")Long groupId,@PathVariable("memberId")Long memberId){
      return ResponseEntity.ok(groupService.deleteGroupMemberFromGroup(groupId,memberId));
    }


    @PatchMapping("/update/group/{groupId}/leader/{leaderId}")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<String> updateTheGroupLeader(@PathVariable("groupId")Long groupId,@PathVariable("leaderId")Long leaderId){
      return ResponseEntity.ok(groupService.updateGroupLeaderFromGroup(groupId,leaderId));
    }


    @PatchMapping("/group/{groupId}/add/member")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public GroupResponse addGroupMemberInExistingGroup(@PathVariable("groupId")Long groupId, @RequestBody AddMemberRequest addMemberRequest){
      return groupService.addGroupMemberInExistingGroup(groupId, addMemberRequest);
    }
}
