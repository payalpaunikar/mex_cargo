package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddQuatationWithPurchaseExcutive {
    private LocalDate requiredQuatationDate;
    private LocalTime requiredQuatationTime;
    private String quatationReferenceNo;
    private Set<Long> purchaseExecutiveIds;
    private Long leadId;
}
