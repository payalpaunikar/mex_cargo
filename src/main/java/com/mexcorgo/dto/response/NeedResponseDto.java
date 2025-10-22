package com.mexcorgo.dto.response;


import com.mexcorgo.component.OtherServices;
import com.mexcorgo.component.RiskCoverageGood;
import com.mexcorgo.datamodel.AdditionalNeed;
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
public class NeedResponseDto {

    private Long needId;

    private String source;

    private String destination;

    private LocalDateTime movingDateAndTime;

    private LocalDateTime receivingDateAndTime;

    private String commodity;

    private String size;

    private String weight;

    private String typeOfTransporatation;

    private String sizeOfTransporatation;

    private String commodityValue;

    private String vehicleValue;

    private String goodTransport;

    private String carTransport;

    private LocalDate carMovingDate;

    private LocalTime carMovingTime;

    private String whenWeGetGoods;

    private String anyThingsElseRatherThanGood;

    private String anyWareHouseFacilityRatherThanThisThings;

    private String insuranceFacilityOfGoods;
    private RiskCoverageGood riskCoverageGood;
    private OtherServices otherServices;
    private List<AdditionalNeed> additionalNeed;

}
