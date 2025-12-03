package com.mexcorgo.dto.response;


import com.mexcorgo.component.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAndUpdateEmployeeDto {
    private Long userId;
    private String userName;
    private String emailId;
    private String mobileNo;
    private EmployeeStatus employeeStatus;
}
