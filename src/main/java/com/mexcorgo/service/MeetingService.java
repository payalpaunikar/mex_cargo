package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.EmailDetailsRequest;
import com.mexcorgo.dto.request.MeetingRequest;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.MeetingRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    private ToDoRepository toDoRepository;

    private MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(ToDoRepository toDoRepository, MeetingRepository meetingRepository) {
        this.toDoRepository = toDoRepository;
        this.meetingRepository = meetingRepository;
    }


    public List<Meeting> addMeetingDetails(Integer counter, List<MeetingRequest> meetingRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getMeetingCount() !=0){
                List<Meeting> getListOfMeetingDetails = meetingRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<Meeting> newMeetingList = meetingRequestList.stream()
                        .map(meetingRequest -> {
                            Meeting meeting = new Meeting();
                            meeting = convertMeetingRequestToMeeting(meeting,meetingRequest);
                            meeting.setToDo(getToDo);
                            return meeting;
                        }).toList();
                getToDo.setMeetingCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfMeetingDetails.addAll(newMeetingList);

                meetingRepository.saveAll(getListOfMeetingDetails);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<Meeting> saveMeetingList = saveTodo.getMeetings();
                return saveMeetingList;
            }

            getToDo.setMeetingCount(counter);
            List<Meeting> meetingList = meetingRequestList.stream()
                    .map(meetingRequest -> {
                        Meeting meeting = new Meeting();
                        meeting = convertMeetingRequestToMeeting(meeting,meetingRequest);
                        meeting.setToDo(getToDo);
                        return meeting;
                    }).toList();

            meetingRepository.saveAll(meetingList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<Meeting> saveMeetingList = saveTodo.getMeetings();

            return saveMeetingList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(counter);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<Meeting> meetingList = meetingRequestList.stream()
                .map(meetingRequest -> {
                    Meeting meeting = new Meeting();
                    meeting = convertMeetingRequestToMeeting(meeting,meetingRequest);
                    meeting.setToDo(newToDo);
                    return meeting;
                }).toList();
        newToDo.setMeetings(meetingList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<Meeting> saveMeetingList = saveTodo.getMeetings();
        return saveMeetingList;
    }

    public List<Meeting> getMeetingDetailsByToDoId(Long toDoId){
        List<Meeting> existingMeetingDetails = meetingRepository.findByToDo_ToDoId(toDoId);
        return existingMeetingDetails;
    }

    public Meeting getMeetingDetailsById(Long meetingId){
        Meeting meetingDetails = meetingRepository.findById(meetingId)
                .orElseThrow(()-> new UserNotFoundException("meeting details with id : "+meetingId+" is not found"));

        return meetingDetails;
    }


    public Meeting updateMeetingDetailsById(Long meetingId,MeetingRequest meetingRequest){
        Meeting existingMeetingDetails = meetingRepository.findById(meetingId)
                .orElseThrow(()-> new UserNotFoundException("meeting details with id : "+meetingId+" is not found"));

       existingMeetingDetails.setName(meetingRequest.getName());
       existingMeetingDetails.setMeetingTime(meetingRequest.getMeetingTime());

       return existingMeetingDetails;
    }


    public String deletedMeetingById(Long meetingId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                .orElseThrow(()-> new ResourceNotFoundException("to do is not found for with id : "+toDoId));
        meetingRepository.deleteById(meetingId);
        existingToDo.setMeetingCount(existingToDo.getMeetingCount()-1);
        toDoRepository.save(existingToDo);
        return "Meeting is deleted succefully";
    }

    private Meeting convertMeetingRequestToMeeting(Meeting meeting,MeetingRequest meetingRequest){
        meeting.setName(meetingRequest.getName());
        meeting.setMeetingTime(meetingRequest.getMeetingTime());
        return meeting;
    }

}
