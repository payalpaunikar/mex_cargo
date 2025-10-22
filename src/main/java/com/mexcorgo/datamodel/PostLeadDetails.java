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
public class PostLeadDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLeadDetailsId;

    private String originFloorNo;
    private String destinationFloorNo;
    private String originDetailAddress;
    private String destinationDetailAddress;

    private Boolean isLiftAvailableAtOrigin;
    private Boolean isLiftAvailableAtDestination;

    private String specialService;
    private String remark;
    private String secondaryVehicle;

    @OneToOne
    @JoinColumn(name = "lead_id",referencedColumnName = "leadId")
    @JsonIgnore
    private Lead lead;
}
