package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyEmplyeeRequest {
    private String employeeName;
    private String department;
    private String designation;
    private String contactNo;
    private String landLineNo;
    private String mailId;
}
