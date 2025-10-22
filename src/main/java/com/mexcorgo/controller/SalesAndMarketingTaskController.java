package com.mexcorgo.controller;


import com.mexcorgo.datamodel.SalesAndMarketingTasks;
import com.mexcorgo.dto.request.CompleteTaskRequestForSales;
import com.mexcorgo.dto.request.SendEmailToAccountExecutiveRequest;
import com.mexcorgo.dto.request.SendEmailToPlanningRequest;
import com.mexcorgo.dto.response.ApiResponse;
import com.mexcorgo.repository.QuatationParticularsAmountRepository;
import com.mexcorgo.repository.SalesAndMarketingTaskRepository;
import com.mexcorgo.service.SalesAndMarketingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class SalesAndMarketingTaskController {

    private SalesAndMarketingTaskService salesAndMarketingTaskService;

    private SalesAndMarketingTaskRepository salesAndMarketingTaskRepository;




    @Autowired
    public SalesAndMarketingTaskController(SalesAndMarketingTaskService salesAndMarketingTaskService,
                                           SalesAndMarketingTaskRepository salesAndMarketingTaskRepository) {
        this.salesAndMarketingTaskService = salesAndMarketingTaskService;
        this.salesAndMarketingTaskRepository = salesAndMarketingTaskRepository;
    }

    @GetMapping("/lead/{leadId}/get/tasks/sales-and-marketing")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public List<SalesAndMarketingTasks> getTasksByLead(@PathVariable Long leadId) {
        return salesAndMarketingTaskService.getTasksByLead(leadId);
    }


//    @PutMapping("/lead/task/{taskId}/tick")
//    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
//    public ResponseEntity<String> markTaskAsCompleted( @PathVariable Long taskId,
//                                                       @RequestBody CompleteTaskRequestForSales request) {
////        SalesAndMarketingTasks task = salesAndMarketingTaskRepository.findById(taskId)
////                .orElseThrow(() -> new RuntimeException("Task not found"));
//
//
//
////        task.setIsCompleted(true);
////        task.setCompletedTaskDate(request.getCompleteTaskDate());
////        task.setCompletedTaskTime(request.getCompleteTaskTime());
////
////
////        String taskName = task.getName().toLowerCase();
////
////        if (taskName.contains("agreement paper sent")) {
////            task.setAgreementNumber(request.getAgreementNumber());
////        }
////
////        if (taskName.contains("agreement confirmation")) {
////            task.setAgreementNumber(request.getAgreementNumber());
////            task.setConfirmationMode(request.getConfirmationMode());
////        }
//
//
//
//
//        salesAndMarketingTaskRepository.save(task);
//
//        return ResponseEntity.ok("Task completed with custom data");
//    }

    @PostMapping("/lead/{leadId}/mark-task")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ResponseEntity<String> markTasks( @PathVariable Long leadId,
                                                       @RequestBody CompleteTaskRequestForSales request){

        List<SalesAndMarketingTasks> salesAndMarketingTasks = salesAndMarketingTaskRepository.findByLead_LeadId(leadId);

        for (SalesAndMarketingTasks task : salesAndMarketingTasks){
            if (task.getName().equalsIgnoreCase("Verbal Confirmation")){
              task.setCompletedTaskDate(request.getVerbalTaskCompltedDate());
              task.setCompletedTaskTime(request.getVerbalTaskCompletedTime());
              task.setIsCompleted(request.getIsVerbalTaskCompleted());
              salesAndMarketingTaskRepository.save(task);

            } else  if (task.getName().equalsIgnoreCase("Get Client Work Order")){
                task.setCompletedTaskDate(request.getWorkOrderTaskCompltedDate());
                task.setCompletedTaskTime(request.getWorkOrderTaskCompletedTime());
                task.setIsCompleted(request.getIsWorkOrderTaskCompleted());
                salesAndMarketingTaskRepository.save(task);
            }
            else  if (task.getName().equalsIgnoreCase("Agreement Paper Sent")){
                task.setAgreementNumber(request.getAgrementSentPaperNumber());
                task.setCompletedTaskDate(request.getAgrementSentPaperTaskCompltedDate());
                task.setCompletedTaskTime(request.getAgrementSentPaperTaskCompletedTime());
                task.setIsCompleted(request.getIsAgrementSentPaperTaskCompleted());
                salesAndMarketingTaskRepository.save(task);

            }
            else  if (task.getName().equalsIgnoreCase("Agreement Confirmation")){
                task.setCompletedTaskDate(request.getAgrementConfirmationTaskCompltedDate());
                task.setCompletedTaskTime(request.getAgrementConfirmationTaskCompltedTime());
                task.setIsCompleted(request.getIsAgrementConfirmationTaskCompleted());
                task.setAgreementNumber(request.getAgrementConfirmationNumber());
                task.setConfirmationMode(request.getAgrementConfirmationMode());
                salesAndMarketingTaskRepository.save(task);
            }
        }

        return ResponseEntity.ok("Task completed with custom data");
    }


    @PostMapping("/send-enduser-details/with/quatation-details/to/planning-executive")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ApiResponse sendEndUserDetailsWithNeedAndQuatationDetailsToPlanningExecutive(@RequestBody SendEmailToPlanningRequest sendEmailToPlanningRequest){
        return salesAndMarketingTaskService.sendEndUserDetailsNeedWithQuatationDetailsToPlanningExecutive(sendEmailToPlanningRequest);
    }


    @PostMapping("/lead/particular-ammount/send/account-executives")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ApiResponse sendParticularAmountToAccountExcutives(@RequestBody SendEmailToAccountExecutiveRequest sendEmailToAccountExecutiveRequest){
      return salesAndMarketingTaskService.sendParticularAmountToAccountExcutives(sendEmailToAccountExecutiveRequest);
    }

}
