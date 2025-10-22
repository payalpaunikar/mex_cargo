package com.mexcorgo.service;


import com.mexcorgo.datamodel.EmailDetails;
import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.ToDo;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.EmailDetailsRequest;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.EmailDetailsRepository;
import com.mexcorgo.repository.ToDoRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmailDetailsService {

       private EmailDetailsRepository emailDetailsRepository;

       private ToDoRepository toDoRepository;

    @Autowired
    public EmailDetailsService(EmailDetailsRepository emailDetailsRepository, ToDoRepository toDoRepository) {
        this.emailDetailsRepository = emailDetailsRepository;
        this.toDoRepository = toDoRepository;
    }

    public List<EmailDetails> addEmailDetails(Integer counter, List<EmailDetailsRequest> emailDetailsRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getEmailDetailsCount() !=0){
                List<EmailDetails> getListOfEmailDetails = emailDetailsRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<EmailDetails> newEmailDetailsList = emailDetailsRequestList.stream()
                        .map(emailDetailsRequest -> {
                            EmailDetails emailDetails = new EmailDetails();
                            emailDetails = convertEmailDetailsRequestToEmailDetails(emailDetails,emailDetailsRequest);
                            emailDetails.setToDo(getToDo);
                            return emailDetails;
                        }).toList();
                getToDo.setEmailDetailsCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfEmailDetails.addAll(newEmailDetailsList);

                emailDetailsRepository.saveAll(getListOfEmailDetails);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<EmailDetails> saveEmailDetailsList = saveTodo.getEmailDetails();
                return saveEmailDetailsList;
            }

            getToDo.setEmailDetailsCount(counter);
            List<EmailDetails> emailDetailsList = emailDetailsRequestList.stream()
                    .map(phoneDetailsRequest -> {
                        EmailDetails emailDetails = new EmailDetails();
                        emailDetails = convertEmailDetailsRequestToEmailDetails(emailDetails,phoneDetailsRequest);
                        emailDetails.setToDo(getToDo);
                        return emailDetails;
                    }).toList();

            emailDetailsRepository.saveAll(emailDetailsList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<EmailDetails> saveEmailDetailsList = saveTodo.getEmailDetails();

            return saveEmailDetailsList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(counter);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(0);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<EmailDetails> emailDetailsList = emailDetailsRequestList.stream()
                .map(emailDetailsRequest -> {
                    EmailDetails emailDetails = new EmailDetails();
                    emailDetails = convertEmailDetailsRequestToEmailDetails(emailDetails,emailDetailsRequest);
                    emailDetails.setToDo(newToDo);
                    return emailDetails;
                }).toList();
        newToDo.setEmailDetails(emailDetailsList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<EmailDetails> saveEmailDetailsList = saveTodo.getEmailDetails();
        return saveEmailDetailsList;
    }

    public List<EmailDetails> getEmailsDetailsByToDoId(Long toDoId){
        List<EmailDetails> existingEmailDetails = emailDetailsRepository.findByToDo_ToDoId(toDoId);
        return existingEmailDetails;
    }


    public EmailDetails getEmailDetailsById(Long emailId){
        EmailDetails emailDetails = emailDetailsRepository.findById(emailId)
                .orElseThrow(()-> new UserNotFoundException("email details with id : "+emailId+" is not found"));

        return emailDetails;
    }


    public EmailDetails updateEmailDetailsById(Long emailId,EmailDetailsRequest emailDetailsRequest){
        EmailDetails existingEmailDetails = emailDetailsRepository.findById(emailId)
                .orElseThrow(()-> new UserNotFoundException("email details with id : "+emailId+" is not found"));

        existingEmailDetails.setEmail(emailDetailsRequest.getEmail());
        existingEmailDetails.setName(emailDetailsRequest.getName());
        existingEmailDetails.setEmailTime(emailDetailsRequest.getEmailTime());

       EmailDetails saveEmailDetails =  emailDetailsRepository.save(existingEmailDetails);

       return saveEmailDetails;
    }


    public String deleteEmailDetailsById(Long emailId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                        .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
      emailDetailsRepository.deleteById(emailId);
     existingToDo.setEmailDetailsCount(existingToDo.getEmailDetailsCount()-1);
     toDoRepository.save(existingToDo);
      return "Email is deleted succefully";
    }

    private EmailDetails convertEmailDetailsRequestToEmailDetails(EmailDetails emailDetails,EmailDetailsRequest emailDetailsRequest){
        emailDetails.setEmail(emailDetailsRequest.getEmail());
        emailDetails.setName(emailDetailsRequest.getName());
        emailDetails.setEmailTime(emailDetailsRequest.getEmailTime());
        return emailDetails;
    }

}
