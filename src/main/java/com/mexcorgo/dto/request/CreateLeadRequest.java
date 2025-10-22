package com.mexcorgo.dto.request;


import com.mexcorgo.component.FollowUpStatus;
import com.mexcorgo.component.OtherServices;
import com.mexcorgo.component.RiskCoverageGood;
import com.mexcorgo.component.WayOfLead;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLeadRequest {
      private LocalDate date;
      private LocalTime time;
      private String modeOfCommunication;
      private WayOfLead wayOfLead;
      private String companyName;
      private String companySector;
      private String companySetUp;
      private String companyHeadOffice;
      private String easyHubCentre;
      private String state;
      private String employeeName;
      private String department;
      private String designation;
      private String contactNo;
      private String landLineNo;
      private String mailId;
      private String endUserName;
     private String endUserDepartment;
    private String endUserDesignation;
    private String endUserContactNo;
    private String endUserLandLineNo;
    private String endUserMailId;
      private String source;
      private String destination;
      private String movingDateAndTime;
      private String receivingDateTime;
      private String commodity;
      private String size;
      private String weight;
      private String typeOfTransportation;
      private String sizeOfTransportation;
      private String commodityValue;
      private String vehicleValue;
      private String goodsTransport;
      private String carTransport;
      private LocalDate carMovingDate;
      private LocalTime carMovingTime;
      private String whenWeGetGoods;
      private String anyThingElseRatherThanGood;
      private String anyWarehouseFacilityRatherThanThisThings;
      private String insuranceFacilityOfGoods;
     // private String commodityAndOtherGoodsInsuranceFacility;
     private RiskCoverageGood riskCoverageGood;
      private OtherServices otherServices;
      private List<AdditionalNeedRequest> additionalNeedRequests;
      private String remark;
      private String remarkDate;
      private String remarkTime;
      private Integer rating;
      private FollowUpStatus followUpStatus;
      private FollowUpRequest followUpRequest;
}
