package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddMasterWithMasterResponseDto {

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

    private Double originalAdditionalServiceCost;

    private Double finalPackageCost;

    private Double finalTrsCost;

    private Double finalCarServiceCost;

    private Double finalAdditionalServiceCost;

    private LocalDateTime responseDate;
}
