package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyResponseDto {

    private Long companyId;
    private String companyName;
    private String companySector;
    private String companySetUp;
    private String headOfOffice;
    private String state;
    private String easyHubCentre;
    private String minorHub;
    private String majorHub;
}
