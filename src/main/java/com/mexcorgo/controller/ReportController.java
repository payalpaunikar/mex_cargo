package com.mexcorgo.controller;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.request.ReportRequest;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ReportController {

        private ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/add/report/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Report> addToDoReportList(@RequestParam("counter")Integer counter, @RequestBody List<ReportRequest> reportRequestList, Authentication authentication){

        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();

        return reportService.addReportList(counter,reportRequestList,currentUser);
    }


    @GetMapping("/get/todo/{toDoId}/report/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<Report> getReportDetailsByToDoId(@PathVariable("toDoId") Long toDoId){
        return reportService.getReportDetailsByToDoId(toDoId);
    }

    @GetMapping("/get/report/{reportId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Report getReportDetailById(@PathVariable("reportId") Long reportId){
        return reportService.getReportDetailsById(reportId);
    }

    @PutMapping("/update/report/{reportId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public Report updateReportDetailsById(@PathVariable("reportId")Long reportId,ReportRequest reportRequest){
        return reportService.updateReportDetails(reportId,reportRequest);
    }


    @DeleteMapping("/delete/report/{reportId}/details")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> deleteReportDetailById(@PathVariable("reportId")Long reportId,
                                                         @RequestParam("toDoId")Long toDoId){
        return ResponseEntity.ok(reportService.deleteReportDetailById(reportId,toDoId));
    }
}
