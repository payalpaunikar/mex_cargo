package com.mexcorgo.dto.request;


import com.mexcorgo.component.FollowUpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowUpRequestWithId {
    private Long followUpId;  // For existing follow-ups
    private LocalDate followUpDate;
    private String followUpRemark;
    private FollowUpStatus followUpStatus;
}
