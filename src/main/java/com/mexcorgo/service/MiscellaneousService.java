package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.MiscellaneousRequest;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.MiscellaneousRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MiscellaneousService {

    private ToDoRepository toDoRepository;

    private MiscellaneousRepository miscellaneousRepository;


    @Autowired
    public MiscellaneousService(ToDoRepository toDoRepository, MiscellaneousRepository miscellaneousRepository) {
        this.toDoRepository = toDoRepository;
        this.miscellaneousRepository = miscellaneousRepository;
    }

    public List<Miscellaneous> addMiscellaneous(Integer counter, List<MiscellaneousRequest> miscellaneousRequests, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getMiscellaneousCount() !=0){
                List<Miscellaneous> getListOfMiscellaneous = miscellaneousRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<Miscellaneous> newMiscellaneousList = miscellaneousRequests.stream()
                        .map(miscellaneousRequest -> {
                            Miscellaneous miscellaneous = new Miscellaneous();
                            miscellaneous = convertMiscellaneouRequestToMiscellaneous(miscellaneous,miscellaneousRequest);
                            miscellaneous.setToDo(getToDo);
                            return miscellaneous;
                        }).toList();
                getToDo.setMiscellaneousCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfMiscellaneous.addAll(newMiscellaneousList);

                miscellaneousRepository.saveAll(getListOfMiscellaneous);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<Miscellaneous> saveMiscellaneousList = saveTodo.getMiscellaneousTasks();
                return saveMiscellaneousList;
            }

            getToDo.setMiscellaneousCount(counter);
            List<Miscellaneous> miscellaneousList = miscellaneousRequests.stream()
                    .map(miscellaneousRequest -> {
                        Miscellaneous miscellaneous = new Miscellaneous();
                        miscellaneous = convertMiscellaneouRequestToMiscellaneous(miscellaneous,miscellaneousRequest);
                        miscellaneous.setToDo(getToDo);
                        return miscellaneous;
                    }).toList();

            miscellaneousRepository.saveAll(miscellaneousList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<Miscellaneous> saveMiscellaneousList = saveTodo.getMiscellaneousTasks();

            return saveMiscellaneousList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(counter);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<Miscellaneous> miscellaneousList = miscellaneousRequests.stream()
                .map(miscellaneousRequest -> {
                    Miscellaneous miscellaneous = new Miscellaneous();
                    miscellaneous = convertMiscellaneouRequestToMiscellaneous(miscellaneous,miscellaneousRequest);
                    miscellaneous.setToDo(newToDo);
                    return miscellaneous;
                }).toList();
        newToDo.setMiscellaneousTasks(miscellaneousList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<Miscellaneous> saveMiscellaneousList = saveTodo.getMiscellaneousTasks();
        return saveMiscellaneousList;
    }

    public List<Miscellaneous> getMiscellaneousDetailsByToDoId(Long toDoId){
        List<Miscellaneous> existingMiscellaneousDetails = miscellaneousRepository.findByToDo_ToDoId(toDoId);
        return existingMiscellaneousDetails;
    }

    public Miscellaneous getMiscellaneousDetailsById(Long miscellaneousId){
        Miscellaneous miscellaneous = miscellaneousRepository.findById(miscellaneousId)
                .orElseThrow(()-> new UserNotFoundException("miscellaneous details with id : "+miscellaneousId+" is not found"));

        return miscellaneous;
    }


    public Miscellaneous updateMiscellaneouDetails(Long miscellaneousId,MiscellaneousRequest miscellaneousRequest){
        Miscellaneous existingMiscellaneousDetails = miscellaneousRepository.findById(miscellaneousId)
                .orElseThrow(()-> new UserNotFoundException("miscellaneous with id : "+miscellaneousId+" is not found"));

        existingMiscellaneousDetails.setTaskName(miscellaneousRequest.getTaskName());
        existingMiscellaneousDetails.setTaskTime(miscellaneousRequest.getTaskTime());

        Miscellaneous saveMiscellaneous = miscellaneousRepository.save(existingMiscellaneousDetails);

        return saveMiscellaneous;
    }


    public String deleteMiscellaneousById(Long miscellaneousId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
        miscellaneousRepository.deleteById(miscellaneousId);
        existingToDo.setMiscellaneousCount(existingToDo.getMiscellaneousCount()-1);
        toDoRepository.save(existingToDo);
        return "miscellaneous deleted succefully";
    }


    private Miscellaneous convertMiscellaneouRequestToMiscellaneous(Miscellaneous miscellaneous,MiscellaneousRequest miscellaneousRequest){
        miscellaneous.setTaskName(miscellaneousRequest.getTaskName());
        miscellaneous.setTaskTime(miscellaneousRequest.getTaskTime());
        return miscellaneous;
    }

}
