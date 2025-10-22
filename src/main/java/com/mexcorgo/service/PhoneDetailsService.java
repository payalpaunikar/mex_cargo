package com.mexcorgo.service;


import com.mexcorgo.datamodel.Meeting;
import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.ToDo;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.PhoneDetailsRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneDetailsService {

        private ToDoRepository toDoRepository;

        private PhoneDetailsRepository phoneDetailsRepository;

        Logger logger = LoggerFactory.getLogger(PhoneDetailsService.class);

    @Autowired
    public PhoneDetailsService(ToDoRepository toDoRepository, PhoneDetailsRepository phoneDetailsRepository) {
        this.toDoRepository = toDoRepository;
        this.phoneDetailsRepository = phoneDetailsRepository;
    }

    public List<PhoneDetails> addPhoneDetails(Integer counter, List<PhoneDetailsRequest> phoneDetailsRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        logger.info("Get eisting to do query work");

        if(existingToDo.isPresent()){
            logger.info("Get eisting to do is present");
            ToDo getToDo = existingToDo.get();
            if(getToDo.getPhoneDetailsCount() !=0){
                logger.info("to do count is not a 0");
                List<PhoneDetails> getListOfPhoneDetails = phoneDetailsRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<PhoneDetails> newPhoneDetails = phoneDetailsRequestList.stream()
                        .map(phoneDetailsRequest -> {
                            PhoneDetails phoneDetails = new PhoneDetails();
                            phoneDetails = convertPhoneDetailsRequestToPhoneDetails(phoneDetails,phoneDetailsRequest);
                            phoneDetails.setToDo(getToDo);
                            return phoneDetails;
                        }).toList();
                getToDo.setPhoneDetailsCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfPhoneDetails.addAll(newPhoneDetails);

                phoneDetailsRepository.saveAll(getListOfPhoneDetails);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<PhoneDetails> savePhoneDetailsList = saveTodo.getPhoneDetails();
                return savePhoneDetailsList;
            }

            logger.info("to do count is not a 0");
            getToDo.setPhoneDetailsCount(counter);
            List<PhoneDetails> phoneDetailsList = phoneDetailsRequestList.stream()
                    .map(phoneDetailsRequest -> {
                        PhoneDetails phoneDetails = new PhoneDetails();
                        phoneDetails = convertPhoneDetailsRequestToPhoneDetails(phoneDetails,phoneDetailsRequest);
                        phoneDetails.setToDo(getToDo);
                        return phoneDetails;
                    }).toList();

            phoneDetailsRepository.saveAll(phoneDetailsList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<PhoneDetails> savePhoneDetailsList = saveTodo.getPhoneDetails();

            return savePhoneDetailsList;

        }

        logger.info("to do is not present for the date");

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(counter);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<PhoneDetails> phoneDetailsList = phoneDetailsRequestList.stream()
                .map(phoneDetailsRequest -> {
                    PhoneDetails phoneDetails = new PhoneDetails();
                    phoneDetails = convertPhoneDetailsRequestToPhoneDetails(phoneDetails,phoneDetailsRequest);
                    phoneDetails.setToDo(newToDo);
                    return phoneDetails;
                }).toList();
        newToDo.setPhoneDetails(phoneDetailsList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<PhoneDetails> savePhoneDetailsList = saveTodo.getPhoneDetails();
        return savePhoneDetailsList;
    }


    public List<PhoneDetails> getPhoneDetailsByToDoId(Long toDoId){
        List<PhoneDetails> existingPhoneDetails = phoneDetailsRepository.findByToDo_ToDoId(toDoId);
        return existingPhoneDetails;
    }

    public PhoneDetails getPhoneDetailsById(Long phoneId){
        PhoneDetails phoneDetails = phoneDetailsRepository.findById(phoneId)
                .orElseThrow(()-> new UserNotFoundException("phone details with id : "+phoneId+" is not found"));

        return phoneDetails;
    }


    public PhoneDetails updatePhoneDetails(Long phoneDetailsId,PhoneDetailsRequest phoneDetailsRequest){
        PhoneDetails existingPhoneDetails = phoneDetailsRepository.findById(phoneDetailsId)
                .orElseThrow(()-> new UserNotFoundException("phone details with id : "+phoneDetailsId+" is not found"));

        existingPhoneDetails.setName(phoneDetailsRequest.getName());
        existingPhoneDetails.setPhoneNumber(phoneDetailsRequest.getPhoneNumber());
        existingPhoneDetails.setPhoneTime(phoneDetailsRequest.getPhoneTime());

        PhoneDetails savePhoneDetails = phoneDetailsRepository.save(existingPhoneDetails);

        return savePhoneDetails;
    }

    public String deletePhoneDetailsById(Long phoneId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
        phoneDetailsRepository.deleteById(phoneId);
        existingToDo.setPhoneDetailsCount(existingToDo.getPhoneDetailsCount()-1);
        toDoRepository.save(existingToDo);
        return "Phone details deleted succefully";
    }



    private PhoneDetails convertPhoneDetailsRequestToPhoneDetails(PhoneDetails phoneDetails,PhoneDetailsRequest phoneDetailsRequest){
        phoneDetails.setPhoneNumber(phoneDetailsRequest.getPhoneNumber());
        phoneDetails.setName(phoneDetailsRequest.getName());
        phoneDetails.setPhoneTime(phoneDetailsRequest.getPhoneTime());
        return phoneDetails;
    }

}
