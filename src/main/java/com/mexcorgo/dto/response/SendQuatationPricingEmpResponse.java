package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendQuatationPricingEmpResponse {
    private Long userId;
    private String userName;
    private String email;
    private String mobileNumber;
    private LocalDate quatationForwardDate;
    private LocalTime quatationForwardTime;
}
