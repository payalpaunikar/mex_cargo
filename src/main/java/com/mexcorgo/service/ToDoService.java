package com.mexcorgo.service;


import com.mexcorgo.component.ToDoEntity;
import com.mexcorgo.dto.response.ToDoEntityCountResponse;
import com.mexcorgo.dto.response.ToDoResponseDto;
import com.mexcorgo.dto.response.ToDoResponseInterface;
import com.mexcorgo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ToDoService {

      private ToDoRepository toDoRepository;

      private PhoneDetailsRepository phoneDetailsRepository;

      private EmailDetailsRepository emailDetailsRepository;

      private WhatsAppDetailsRepository whatsAppDetailsRepository;

      private NetSearchingRepository netSearchingRepository;

      private PhysicalMeetingRepository physicalMeetingRepository;

      private ReportRepository reportRepository;

      private MeetingRepository meetingRepository;

      private MiscellaneousRepository miscellaneousRepository;


    @Autowired
    public ToDoService(ToDoRepository toDoRepository, PhoneDetailsRepository phoneDetailsRepository, EmailDetailsRepository emailDetailsRepository, WhatsAppDetailsRepository whatsAppDetailsRepository, NetSearchingRepository netSearchingRepository, PhysicalMeetingRepository physicalMeetingRepository, ReportRepository reportRepository, MeetingRepository meetingRepository, MiscellaneousRepository miscellaneousRepository) {
        this.toDoRepository = toDoRepository;
        this.phoneDetailsRepository = phoneDetailsRepository;
        this.emailDetailsRepository = emailDetailsRepository;
        this.whatsAppDetailsRepository = whatsAppDetailsRepository;
        this.netSearchingRepository = netSearchingRepository;
        this.physicalMeetingRepository = physicalMeetingRepository;
        this.reportRepository = reportRepository;
        this.meetingRepository = meetingRepository;
        this.miscellaneousRepository = miscellaneousRepository;
    }


    public ToDoEntityCountResponse getEntityCount(Long userId, LocalDate currentDate, ToDoEntity entity) {
        ToDoEntityCountResponse toDoEntityCountResponse = new ToDoEntityCountResponse();
        switch (entity) {
            case PHONE ->{
                Integer getCount = toDoRepository.getPhoneDetailsCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case EMAIL -> {
                Integer getCount = toDoRepository.getEmailDetailsCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case WHATSAPP -> {
                Integer getCount = toDoRepository.getWhatsAppDetailsCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case PHYSICAL_MEETING -> {
                Integer getCount = toDoRepository.getPhysicalMeetingCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case NET_SEARCHING -> {
                Integer getCount = toDoRepository.getNetSerchingCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case MEETING -> {
                Integer getCount = toDoRepository.getMeetingCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case REPORT -> {
                Integer getCount = toDoRepository.getReportCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            case MISCELLANEOUS -> {
                Integer getCount = toDoRepository.getMiscellaneousCount(userId, currentDate);
                toDoEntityCountResponse.setCount((getCount!=null)?getCount:0);
                return toDoEntityCountResponse;
            }
            default -> {
                throw new IllegalArgumentException("Invalid ToDoEntity");
            }
        }
    }


    public Page<ToDoResponseDto> getToDoEntityCountList(Long userId, int page, int size){

        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Order.desc("toDoDate"),Sort.Order.desc("toDoId")));

        Page<ToDoResponseInterface> toDoResponseInterfaces = toDoRepository.getToDoEntityCountList(userId,pageable);

        Page<ToDoResponseDto> toDoResponseDtos = toDoResponseInterfaces.map(toDoResponseInterface -> {
            ToDoResponseDto toDoResponseDto = new ToDoResponseDto();
            toDoResponseDto.setToDoId(toDoResponseInterface.getToDoId());
            toDoResponseDto.setToDoDate(toDoResponseInterface.getToDoDate());
            toDoResponseDto.setMeetingCount(toDoResponseInterface.getMeetingCount());
            toDoResponseDto.setMiscellaneousCount(toDoResponseInterface.getMiscellaneousCount());
            toDoResponseDto.setReportCount(toDoResponseInterface.getReportCount());
            toDoResponseDto.setEmailDetailsCount(toDoResponseInterface.getEmailDetailsCount());
            toDoResponseDto.setPhoneDetailsCount(toDoResponseInterface.getPhoneDetailsCount());
            toDoResponseDto.setPhysicalMeetingCount(toDoResponseInterface.getPhysicalMeetingCount());
            toDoResponseDto.setNetSerchingCount(toDoResponseInterface.getNetSerchingCount());
            toDoResponseDto.setWhatsAppDetailsCount(toDoResponseInterface.getWhatsAppDetailsCount());

            return toDoResponseDto;
        });

        return toDoResponseDtos;
    }



}
