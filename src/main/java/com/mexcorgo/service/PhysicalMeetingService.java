package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.request.PhysicalMeetingRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.PhysicalMeetingRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PhysicalMeetingService {

    private ToDoRepository toDoRepository;
    private PhysicalMeetingRepository physicalMeetingRepository;

    @Autowired
    public PhysicalMeetingService(ToDoRepository toDoRepository, PhysicalMeetingRepository physicalMeetingRepository) {
        this.toDoRepository = toDoRepository;
        this.physicalMeetingRepository = physicalMeetingRepository;
    }

    public List<PhysicalMeeting> addPhysicalMeeting(Integer counter, List<PhysicalMeetingRequest> physicalMeetingRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getPhysicalMeetingCount() !=0){
                List<PhysicalMeeting> getListOfPhysicalMeeting = physicalMeetingRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<PhysicalMeeting> newPhysicalMeeting = physicalMeetingRequestList.stream()
                        .map(physicalMeetingRequest -> {
                            PhysicalMeeting physicalMeeting = new PhysicalMeeting();
                            physicalMeeting = convertPhysicalMeetingRequestToPhysicalMeeting(physicalMeeting,physicalMeetingRequest);
                            physicalMeeting.setToDo(getToDo);
                            return physicalMeeting;
                        }).toList();
                getToDo.setPhysicalMeetingCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfPhysicalMeeting.addAll(newPhysicalMeeting);

                physicalMeetingRepository.saveAll(getListOfPhysicalMeeting);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<PhysicalMeeting> savePhysicalMeetingList = saveTodo.getPhysicalMeetings();
                return savePhysicalMeetingList;
            }

            getToDo.setPhysicalMeetingCount(counter);
            List<PhysicalMeeting> physicalMeetingList = physicalMeetingRequestList.stream()
                    .map(physicalMeetingRequest -> {
                        PhysicalMeeting physicalMeeting = new PhysicalMeeting();
                        physicalMeeting = convertPhysicalMeetingRequestToPhysicalMeeting(physicalMeeting,physicalMeetingRequest);
                        physicalMeeting.setToDo(getToDo);
                        return physicalMeeting;
                    }).toList();

            physicalMeetingRepository.saveAll(physicalMeetingList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<PhysicalMeeting> savePhysicalMeetingList = saveTodo.getPhysicalMeetings();

            return savePhysicalMeetingList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(counter);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<PhysicalMeeting> physicalMeetingList = physicalMeetingRequestList.stream()
                .map(physicalMeetingRequest -> {
                    PhysicalMeeting physicalMeeting = new PhysicalMeeting();
                    physicalMeeting = convertPhysicalMeetingRequestToPhysicalMeeting(physicalMeeting,physicalMeetingRequest);
                    physicalMeeting.setToDo(newToDo);
                    return physicalMeeting;
                }).toList();
        newToDo.setPhysicalMeetings(physicalMeetingList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<PhysicalMeeting> savePhysicalMeetingList = saveTodo.getPhysicalMeetings();
        return savePhysicalMeetingList;
    }


    public List<PhysicalMeeting> getPhysicalDetailsByToDoId(Long toDoId){
        List<PhysicalMeeting> existingPhysicalDetails = physicalMeetingRepository.findByToDo_ToDoId(toDoId);
        return existingPhysicalDetails;
    }


    public PhysicalMeeting getPhysicalMeetingDetailsById(Long physicalMeetingId){
        PhysicalMeeting physicalMeetingDetails = physicalMeetingRepository.findById(physicalMeetingId)
                .orElseThrow(()-> new UserNotFoundException("physical meeting details with id : "+physicalMeetingId+" is not found"));

        return physicalMeetingDetails;
    }

    public PhysicalMeeting updatePhysicalMeetingDetails(Long physicalMeetingId,PhysicalMeetingRequest physicalMeetingRequest){
        PhysicalMeeting existingPhysicalMeeting = physicalMeetingRepository.findById(physicalMeetingId)
                .orElseThrow(()-> new UserNotFoundException("physical meeting with id : "+physicalMeetingId+" is not found"));

        existingPhysicalMeeting.setName(physicalMeetingRequest.getName());
        existingPhysicalMeeting.setMeetingTime(physicalMeetingRequest.getMeetingTime());
        existingPhysicalMeeting.setLocation(physicalMeetingRequest.getLocation());

        PhysicalMeeting savePhysicalMeeting = physicalMeetingRepository.save(existingPhysicalMeeting);

        return savePhysicalMeeting;
    }


     public String deletePhysicalMeetingById(Long physicalMeetingId,Long toDoId){
         ToDo existingToDo = toDoRepository.findById(toDoId)
                 .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
         physicalMeetingRepository.deleteById(physicalMeetingId);
         existingToDo.setPhysicalMeetingCount(existingToDo.getPhysicalMeetingCount()-1);
         toDoRepository.save(existingToDo);
         return "Physical Meeting deleted succefully";
     }

    private PhysicalMeeting convertPhysicalMeetingRequestToPhysicalMeeting(PhysicalMeeting physicalMeeting,PhysicalMeetingRequest physicalMeetingRequest){
       physicalMeeting.setName(physicalMeetingRequest.getName());
       physicalMeeting.setMeetingTime(physicalMeetingRequest.getMeetingTime());
       physicalMeeting.setLocation(physicalMeetingRequest.getLocation());
       return physicalMeeting;
    }


}
