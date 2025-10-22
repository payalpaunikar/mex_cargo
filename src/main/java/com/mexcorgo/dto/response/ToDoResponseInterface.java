package com.mexcorgo.dto.response;

import java.time.LocalDate;

public interface ToDoResponseInterface {
    Long getToDoId();
    LocalDate getToDoDate();
    Integer getPhoneDetailsCount();
    Integer getEmailDetailsCount();
    Integer getWhatsAppDetailsCount();
    Integer getNetSerchingCount();
    Integer getPhysicalMeetingCount();
    Integer getReportCount();
    Integer getMeetingCount();
    Integer getMiscellaneousCount();
}
