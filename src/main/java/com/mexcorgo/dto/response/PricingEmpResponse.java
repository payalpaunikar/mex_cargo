package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PricingEmpResponse {
    private Long userId;
    private String userName;
    private String email;
    private String mobileNumber;
}
