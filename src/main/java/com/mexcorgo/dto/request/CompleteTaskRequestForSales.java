package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompleteTaskRequestForSales {

//    private LocalDate completeTaskDate;
//    private LocalTime completeTaskTime;
//    private String agreementNumber;            // Only for agreement tasks
//    private String confirmationMode;           // Only for "Agreement Confirmation" task

      private LocalDate verbalTaskCompltedDate;
      private LocalTime verbalTaskCompletedTime;
      private Boolean  isVerbalTaskCompleted;
    private LocalDate workOrderTaskCompltedDate;
    private LocalTime workOrderTaskCompletedTime;
    private Boolean  isWorkOrderTaskCompleted;
    private LocalDate agrementSentPaperTaskCompltedDate;
    private LocalTime agrementSentPaperTaskCompletedTime;
    private Boolean  isAgrementSentPaperTaskCompleted;
    private String agrementSentPaperNumber;
    private LocalDate agrementConfirmationTaskCompltedDate;
    private LocalTime agrementConfirmationTaskCompltedTime;
    private Boolean  isAgrementConfirmationTaskCompleted;
    private String agrementConfirmationMode;
    private String agrementConfirmationNumber;
}
