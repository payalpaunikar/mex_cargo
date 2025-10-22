package com.mexcorgo.dto.response;


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
public class QutationSelectedMasterForServiceResponse {
        private Long id;
        private ServiceType serviceType;

    private Long masterId;

    private String associateCode;

    private String serviceSector;

    private String companyName;

    private String contactName;

    private String contactNumber;

    private String emailId;

    private Integer grade;

    private String location;

    private String hub;

    private String state;

    private BigDecimal finalServiceAmount;
}
