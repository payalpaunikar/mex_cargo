package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceCompanyRequest {

    private String comapnyName;
    private String email;
    private String phone;
    private String address;
    private Set<Long> serviceIds;
}
