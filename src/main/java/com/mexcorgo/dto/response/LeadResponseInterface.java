package com.mexcorgo.dto.response;

import com.mexcorgo.component.DataReference;
import com.mexcorgo.component.FollowUpStatus;
import com.mexcorgo.component.ModeOfCommunication;
import com.mexcorgo.component.WayOfLead;


import java.time.LocalDate;
import java.time.LocalTime;


public interface LeadResponseInterface {

//    Long leadId();
//    String leadReferenceNo();
//    LocalDate leadDate();
//    LocalTime leadTime();
//    WayOfLead wayOfLead();
//    String modeOfCommunication();

    Long getLeadId();
    String getLeadReferenceNo();
    LocalDate getLeadDate();
    LocalTime getLeadTime();
    WayOfLead getWayOfLead();
    ModeOfCommunication getModeOfCommunication();
    DataReference getDataReference();
    Boolean getIsQuatationCreated();
    Boolean getIsQuatationSendToUser();
    FollowUpStatus getfollowUpStatus();
    Boolean getIsVerbalConfirmationTaskCompleted();

}
