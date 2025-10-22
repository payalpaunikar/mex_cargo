package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponse {
    private Long userId;
    private String userName;
    private String emailId;
    private String mobileNo;
    private Long roleId;
    private Long departmentId;
}
