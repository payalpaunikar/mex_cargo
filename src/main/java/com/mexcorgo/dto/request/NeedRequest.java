package com.mexcorgo.dto.request;


import com.mexcorgo.component.OtherServices;
import com.mexcorgo.component.RiskCoverageGood;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NeedRequest {
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

    private String whenWeGetGoods;

    private String anyThingsElseRatherThanGood;

    private String anyWareHouseFacilityRatherThanThisThings;

    private String insuranceFacilityOfGoods;

    private RiskCoverageGood riskCoverageGood;

    private OtherServices otherServices;

    private String commudityAndOtherGoodsInsuranceFacility;

    private List<AdditionalNeedRequestWithId> additionalNeedRequestList;
}
