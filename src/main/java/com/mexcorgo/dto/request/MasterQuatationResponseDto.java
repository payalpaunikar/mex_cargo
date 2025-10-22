package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MasterQuatationResponseDto {

    private Long quotationMasterId;
    private Double originalPackageCost;
    private Double originalTrsCost;
    private Double originalCarServiceCost;
    private Double originalAdditionalCost;
    private Double finalPackageCost;
    private Double finalTrsCost;
    private Double finalCarServiceCost;
    private Double finalAdditionalCost;
}
