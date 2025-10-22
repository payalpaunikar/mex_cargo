package com.mexcorgo.dto.request;


import com.mexcorgo.datamodel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SendEmailToPlanningRequest {
    private Long leadId;
    private Set<Long> planningExecutiveIds;
}
