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
public class NeedExtraDetails {


       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long needExtraDetailsId;

       @OneToOne
       @JoinColumn(name = "lead_id")
       @JsonIgnore
       private Lead lead;

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
