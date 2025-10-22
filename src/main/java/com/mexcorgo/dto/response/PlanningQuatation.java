package com.mexcorgo.dto.response;


import com.mexcorgo.component.OtherServices;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanningQuatation {
    private Long LeadId;
    private String leadReferenceNo;
    private String source;
    private String destination;
    private Long quatationId;
    private String commodity;
    private String size;
    private String weight;
    private String typeOfTransporatation;
    private LocalDateTime movingDateAndTime;
    private LocalDate requiredQuatationDate;
    private LocalTime requiredQuatationTime;
    private LocalDate receivingQuatationDate;
    private LocalTime receivingQuationTime;
    private String quatationReferenceNo;
    private OtherServices otherServices;
    private String carTransport;
    private LocalDate carMovingDate;
    private LocalTime carMovingTime;
}
