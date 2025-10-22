package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteRequest {
    private String remark;
    private LocalDate date;
    private LocalTime time;
    private Integer rating;
    private List<FollowUpRequestWithId> followUps;
}
