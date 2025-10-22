package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ToDoResponseDto {

    private Long toDoId;
    private LocalDate toDoDate;
    private Integer phoneDetailsCount;
    private Integer emailDetailsCount;
    private Integer whatsAppDetailsCount;
    private Integer netSerchingCount;
    private Integer physicalMeetingCount;
    private Integer reportCount;
    private Integer meetingCount;
    private Integer miscellaneousCount;
}
