package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetLeadFullDetails {

       private LeadResponseDto lead;
       private CompanyResponseDto company;
       private CompanyEmployeeResponseDto companyEmployee;
       private NeedResponseDto need;
}
