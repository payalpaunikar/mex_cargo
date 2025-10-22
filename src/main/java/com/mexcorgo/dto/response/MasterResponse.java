package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MasterResponse {

    private Long quotationMasterId;

    private Long masterId;

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
}
