package com.mexcorgo.dto.response;

import com.mexcorgo.component.DataReference;
import com.mexcorgo.component.FollowUpStatus;
import com.mexcorgo.component.ModeOfCommunication;
import com.mexcorgo.component.WayOfLead;
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
public class LeadResponseDto {
    private Long leadId;
    private String leadReferenceNo;
    private LocalDate leadDate;
    private LocalTime leadTime;
    private WayOfLead wayOfLead;
    private ModeOfCommunication modeOfCommunication;
    private DataReference dataReference;
    private Boolean isQuatationCreated;
    private Boolean isQuatationSendToUser;
    private FollowUpStatus followUpStatus;
    private Boolean isVerbalConfirmationTaskCompleted;
}
