package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddQuatationAnalyzePricingResponse {
    private Long quatationId;
    private Double analyzePackageCost;
    private Double analyzeTrsCost;
    private Double analyzeCarServiceCost;
    private Double analyzeAdditionalServiceCost;
}
