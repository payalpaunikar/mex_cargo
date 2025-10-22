package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinalizeQuatationRequest {

    private Long quatationMasterId;
    private Double companyPackageCost;
    private Double companyTrsCost;
    private Double companyCarServiceCost;
    private Double companyAdditionalServiceCost;
}
