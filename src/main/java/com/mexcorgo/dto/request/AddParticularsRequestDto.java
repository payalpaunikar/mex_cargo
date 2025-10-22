package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddParticularsRequestDto {
    private Double packingAmount;
    private Double loadingAmount;
    private Double unloadingAmount;
    private Double unpackingAmount;
    private Double packingAndLoadingAmount;
    private Double unloadingAndUnpackingAmount;
    private Double packingAndLoadingAndUnloadingAndUnpackingAmount;
    private Double transportationOfHouseholdAmount;

}
