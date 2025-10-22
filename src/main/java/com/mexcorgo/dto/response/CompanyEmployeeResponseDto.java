package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyEmployeeResponseDto {

    private Long companyEmployeeId;
    private String employeeName;
    private String department;
    private String designation;
    private String contactNo;
    private String landLineNo;
    private String mailId;
}
