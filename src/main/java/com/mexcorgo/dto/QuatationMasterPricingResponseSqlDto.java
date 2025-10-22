package com.mexcorgo.dto;


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
public class QuatationMasterPricingResponseSqlDto {
    private Long leadId;
    private String leadReferenceNo;
    private String source;
    private String destination;
    private String commodity;
    private String size;
    private String weight;
    private String typeOfTransporatation;
    private Long quatationId;
    private LocalDateTime movingDateAndTime;
    private OtherServices otherServices;
    private String carTransport;
    private LocalDate carMovingDate;
    private LocalTime carMovingTime;
    private LocalDate quatationRequiredDate;
    private LocalTime quatationRequiredTime;
    private String quatationReferenceNo;
    private Long masterId;
    private String associateCode;
    private String serviceSector;
    private String companyName;
    private String contactName;
    private String contactNumber;
    private String emailId;
    private Integer grade;
    private String location;
    private String hub;
    private String state;
    private Double originalPackageCost;
    private Double originalTrsCost;
    private Double originalCarServiceCost;
    private Double originalAdditionalCost;
    private Double finalPackageCost;
    private Double finalTrsCost;
    private Double finalCarServiceCost;
    private Double finalAdditionalCost;

}
