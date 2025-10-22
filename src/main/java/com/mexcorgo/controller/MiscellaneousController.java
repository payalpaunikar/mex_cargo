package com.mexcorgo.controller;


import com.mexcorgo.datamodel.Meeting;
import com.mexcorgo.datamodel.Miscellaneous;
import com.mexcorgo.datamodel.PhysicalMeeting;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.MiscellaneousRequest;
import com.mexcorgo.dto.request.PhysicalMeetingRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.MiscellaneousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MiscellaneousController {

    private MiscellaneousService miscellaneousService;

    @Autowired
    public MiscellaneousController(MiscellaneousService miscellaneousService) {
        this.miscellaneousService = miscellaneousService;
    }

    @PostMapping("/add/miscellaneous/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Miscellaneous> addToDoMiscellaneous(@RequestParam("counter")Integer counter, @RequestBody List<MiscellaneousRequest> miscellaneousRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return miscellaneousService.addMiscellaneous(counter,miscellaneousRequestList,currentUser);

    }

    @GetMapping("/get/todo/{toDoId}/miscellaneous/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Miscellaneous> getMiscellaneousDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return miscellaneousService.getMiscellaneousDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/miscellaneous/{miscellaneousId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Miscellaneous getMiscellaneousDetailById(@PathVariable("miscellaneousId") Long miscellaneousId){
        return miscellaneousService.getMiscellaneousDetailsById(miscellaneousId);
    }


    @PutMapping("/update/miscellaneous/{miscellaneousId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Miscellaneous updateMiscellaneousDetailsById(@PathVariable("miscellaneousId")Long miscellaneousId,MiscellaneousRequest miscellaneousRequest){
        return miscellaneousService.updateMiscellaneouDetails(miscellaneousId,miscellaneousRequest);
    }


     @DeleteMapping("/delete/miscellaneous/{miscellaneousId}/details")
     @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
     public ResponseEntity<String> deleteMiscellaneousById(@PathVariable("miscellaneousId") Long miscellaneousId,
                                                           @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(miscellaneousService.deleteMiscellaneousById(miscellaneousId,toDoId));
     }
}
