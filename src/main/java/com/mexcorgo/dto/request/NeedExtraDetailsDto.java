package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NeedExtraDetailsDto {

    private String originFloorNo;

    private String destinationFloorNo;

    private String originDetailsAddress;

    private String destinationDetailsAddress;

    private Boolean isLiftAvailableInOrigin;

    private Boolean isLiftAvailableInDestination;

    private String specialService;

    private String secondaryVehicle;

    private String remark;
}
