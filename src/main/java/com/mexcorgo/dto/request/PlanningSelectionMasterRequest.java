package com.mexcorgo.dto.request;


import com.mexcorgo.component.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanningSelectionMasterRequest {

       private Long quatationId;
       private ServiceType serviceType;
       private Long quatationMasterId;
       private BigDecimal finalServiceAmount;
}
