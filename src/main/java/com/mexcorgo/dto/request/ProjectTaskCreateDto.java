package com.mexcorgo.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ProjectTaskCreateDto {

    private String taskName;
    private LocalDate idealDate;
    private LocalTime idealTime;
    private LocalDate implementedDate;
    private LocalTime implementedTime;
    private Long quatationProjectExecutiveId;
}
