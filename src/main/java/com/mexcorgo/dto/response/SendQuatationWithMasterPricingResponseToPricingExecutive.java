package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendQuatationWithMasterPricingResponseToPricingExecutive {

    private String leadReferenceNo;
    private String source;
    private String destination;
    private LocalDateTime movingDateAndTime;
    private LocalDate quatationRequiredDate;
    private LocalTime quatationRequiredTime;
    private String quatationReferenceNo;
    private List<MasterWithPrincingResponse> masterWithPrincingResponses;
}
