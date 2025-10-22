package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class QuatationParticularsAmount {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long quatationParticularsAmountId;

       @OneToOne
       @JoinColumn(name = "quatation_id")
       @JsonIgnore
       private Quatation quatation;

    private Double packingAmount;
    private Double loadingAmount;
    private Double unloadingAmount;
    private Double unpackingAmount;
    private Double packingAndLoadingAmount;
    private Double unloadingAndUnpackingAmount;
    private Double packingAndLoadingAndUnloadingAndUnpackingAmount;
    private Double transportationOfHouseholdAmount;
}
