package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddQuatationAnalyzePricingDto {
    private Double analyzePackageCost;
    private Double analyzeTrsCost;
    private Double analyzeCarServiceCost;
    private Double analyzeAdditionalServiceCost;
}
