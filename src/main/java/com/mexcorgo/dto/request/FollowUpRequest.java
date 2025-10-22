package com.mexcorgo.dto.request;


import com.mexcorgo.component.FollowUpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowUpRequest {
    private String followUpDate;
    private String followUpRemark;
}
