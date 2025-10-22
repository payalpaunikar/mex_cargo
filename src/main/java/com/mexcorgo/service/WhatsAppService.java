package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.request.WhatsAppDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.ToDoRepository;
import com.mexcorgo.repository.WhatsAppDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WhatsAppService {

    private ToDoRepository toDoRepository;

    private WhatsAppDetailsRepository whatsAppDetailsRepository;


    @Autowired
    public WhatsAppService(ToDoRepository toDoRepository, WhatsAppDetailsRepository whatsAppDetailsRepository) {
        this.toDoRepository = toDoRepository;
        this.whatsAppDetailsRepository = whatsAppDetailsRepository;
    }

    public List<WhatsAppDetails> addWhatsAppDetailsList(Integer counter, List<WhatsAppDetailsRequest> whatsAppDetailsRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getWhatsAppDetailsCount() !=0){
                List<WhatsAppDetails> getWhatsAppDetailsList = whatsAppDetailsRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<WhatsAppDetails> newWhatsAppDetails = whatsAppDetailsRequestList.stream()
                        .map(whatsAppDetailsRequest -> {
                            WhatsAppDetails whatsAppDetails = new WhatsAppDetails();
                            whatsAppDetails = convertWhatsAppDetailsRequestToWhatsAppDetails(whatsAppDetails,whatsAppDetailsRequest);
                            whatsAppDetails.setToDo(getToDo);
                            return whatsAppDetails;
                        }).toList();
                getToDo.setWhatsAppDetailsCount(counter);
                //merge old phone details to newPhoneDetails
                getWhatsAppDetailsList.addAll(newWhatsAppDetails);

                whatsAppDetailsRepository.saveAll(getWhatsAppDetailsList);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<WhatsAppDetails> saveWhatsAppDetailsList = saveTodo.getWhatsAppDetails();
                return saveWhatsAppDetailsList;
            }

            getToDo.setWhatsAppDetailsCount(counter);
            List<WhatsAppDetails> whatsAppDetailsList = whatsAppDetailsRequestList.stream()
                    .map(whatsAppDetailsRequest -> {
                        WhatsAppDetails whatsAppDetails = new WhatsAppDetails();
                        whatsAppDetails = convertWhatsAppDetailsRequestToWhatsAppDetails(whatsAppDetails,whatsAppDetailsRequest);
                        whatsAppDetails.setToDo(getToDo);
                        return whatsAppDetails;
                    }).toList();

            whatsAppDetailsRepository.saveAll(whatsAppDetailsList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<WhatsAppDetails> saveWhatsAppDetailsList = saveTodo.getWhatsAppDetails();

            return saveWhatsAppDetailsList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(counter);
        newToDo.setUser(currentUser);
        List<WhatsAppDetails> whatsAppDetailsList = whatsAppDetailsRequestList.stream()
                .map(whatsAppDetailsRequest -> {
                    WhatsAppDetails whatsAppDetails = new WhatsAppDetails();
                    whatsAppDetails = convertWhatsAppDetailsRequestToWhatsAppDetails(whatsAppDetails,whatsAppDetailsRequest);
                    whatsAppDetails.setToDo(newToDo);
                    return whatsAppDetails;
                }).toList();
        newToDo.setWhatsAppDetails(whatsAppDetailsList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<WhatsAppDetails> saveWhatsAppDetailsList = saveTodo.getWhatsAppDetails();
        return saveWhatsAppDetailsList;
    }


    public List<WhatsAppDetails> getWhatsAppDetailsByToDoId(Long toDoId){
        List<WhatsAppDetails> existingWhatsAppDetails = whatsAppDetailsRepository.findByToDo_ToDoId(toDoId);
        return existingWhatsAppDetails;
    }


    public WhatsAppDetails getWhatsAppDetailsById(Long whatsAppId){
        WhatsAppDetails whatsAppDetails = whatsAppDetailsRepository.findById(whatsAppId)
                .orElseThrow(()-> new UserNotFoundException("whatsapp details with id : "+whatsAppId+" is not found"));

        return whatsAppDetails;
    }


    public WhatsAppDetails updateWhatsAppDetails(Long whatsAppId,WhatsAppDetailsRequest whatsAppDetailsRequest){
        WhatsAppDetails existingWhatsApp = whatsAppDetailsRepository.findById(whatsAppId)
                .orElseThrow(()-> new UserNotFoundException("whatsapp details with id : "+whatsAppId+" is not found"));

        existingWhatsApp.setName(whatsAppDetailsRequest.getName());
        existingWhatsApp.setWhatsAppNumber(whatsAppDetailsRequest.getWhatsAppNumber());
        existingWhatsApp.setWhatsAppTime(whatsAppDetailsRequest.getWhatsAppTime());

        WhatsAppDetails saveWhatsAppDetails = whatsAppDetailsRepository.save(existingWhatsApp);

        return saveWhatsAppDetails;
    }


      public String deleteWhatsAppDetailById(Long whatsAppId,Long toDoId){
          ToDo existingToDo = toDoRepository.findById(toDoId)
                  .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
        whatsAppDetailsRepository.deleteById(whatsAppId);
        existingToDo.setWhatsAppDetailsCount(existingToDo.getWhatsAppDetailsCount()-1);
        toDoRepository.save(existingToDo);
        return "WhatsApp Detail deleted succefully";
      }


    private WhatsAppDetails convertWhatsAppDetailsRequestToWhatsAppDetails(WhatsAppDetails whatsAppDetails,WhatsAppDetailsRequest whatsAppDetailsRequest){
       whatsAppDetails.setName(whatsAppDetailsRequest.getName());
       whatsAppDetails.setWhatsAppNumber(whatsAppDetailsRequest.getWhatsAppNumber());
       whatsAppDetails.setWhatsAppTime(whatsAppDetailsRequest.getWhatsAppTime());
       return whatsAppDetails;
    }

}
