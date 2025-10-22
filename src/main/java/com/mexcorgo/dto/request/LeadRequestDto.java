package com.mexcorgo.dto.request;


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
public class LeadRequestDto {

    private String leadReferenceNo;
    private LocalDate leadDate;
    private LocalTime leadTime;
    private WayOfLead wayOfLead;
    private String modeOfCommunication;
}
