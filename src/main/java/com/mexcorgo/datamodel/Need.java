package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mexcorgo.component.Commodity;
import com.mexcorgo.component.OtherServices;
import com.mexcorgo.component.RiskCoverageGood;
import jakarta.persistence.*;
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
@Entity
public class Need {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long needId;

    private String source;

    private String destination;

    private String originFloorNo;

    private String destinationFloorNo;

    private String originDetailsAddress;

    private String destinationDetailsAddress;

    private Boolean isLiftAvailableInOrigin;

    private Boolean isLiftAvailableInDestination;

    private String specialService;

    private String secondaryVehicle;

    private LocalDateTime movingDateAndTime;

    private LocalDateTime receivingDateAndTime;

    @Enumerated(EnumType.STRING)
    private Commodity commodity;

    private String size;

    private String weight;

    private String secondWeightValue;

    private String overAllWeightValue;

    private String typeOfTransporatation;

    private String sizeOfTransporatation;

    private String preferredRoot;

    private String commodityValue;

    private String vehicleValue;

    private String goodTransport;

    private String carTransport;

    private LocalDate carMovingDate;

    private LocalTime carMovingTime;

    private LocalDate carReceivingDate;

    private LocalTime carReceivingTime;

    private String whenWeGetGoods;

    private String anyThingsElseRatherThanGood;

    private String anyWareHouseFacilityRatherThanThisThings;

 //   private String insuranceFacilityOfGoods;

    @Enumerated(EnumType.STRING)
    private RiskCoverageGood riskCoverageGood;

    @Enumerated(EnumType.STRING)
    private OtherServices otherServices;

    @OneToMany(mappedBy = "need",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<AdditionalNeed> additionalNeed;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Lead lead;

}
