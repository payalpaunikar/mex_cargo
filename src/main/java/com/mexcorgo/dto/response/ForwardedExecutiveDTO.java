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
public class ForwardedExecutiveDTO {

    private Long userId;
    private String userName;
    private String email;
    private LocalDate quatationForwardDate;
    private LocalTime quatationForwardTime;
}
