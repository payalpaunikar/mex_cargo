package com.mexcorgo.controller;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.NetSearchingRequest;
import com.mexcorgo.dto.request.PhysicalMeetingRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.NetSearchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class NetSearchingController {

    public NetSearchingService netSearchingService;

    @Autowired
    public NetSearchingController(NetSearchingService netSearchingService) {
        this.netSearchingService = netSearchingService;
    }


    @PostMapping("/add/netsearching/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<NetSearching> addToDoNetSearching(@RequestParam("counter")Integer counter, @RequestBody List<NetSearchingRequest> netSearchingRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return netSearchingService.addNetSearching(counter,netSearchingRequestList,currentUser);

    }

    @GetMapping("/get/todo/{toDoId}/netsearching/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<NetSearching> getNetSearchingDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return netSearchingService.getNetSearchingDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/netsearching/{netSearchingId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public NetSearching getNetSearchingDetailById(@PathVariable("netSearchingId") Long netSearchingId){
        return netSearchingService.getNetSearchingDetailsById(netSearchingId);
    }


     @PutMapping("/update/netsearching/{netSearchingId}/details")
     @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
     public NetSearching updateNetSearchingDetails(@PathVariable("netSearchingId")Long netSearchingId,NetSearchingRequest netSearchingRequest){
        return netSearchingService.updateNetSearchingDetails(netSearchingId,netSearchingRequest);
     }


     @DeleteMapping("/delete/netsearching/{netSearchingId}/details")
     @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
     public ResponseEntity<String> deleteNetSearchingById(@PathVariable("netSearchingId") Long netSearchingId,
                                                          @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(netSearchingService.deleteNetSearchingById(netSearchingId,toDoId));
     }

}
