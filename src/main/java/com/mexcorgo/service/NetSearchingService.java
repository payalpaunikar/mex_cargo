package com.mexcorgo.service;


import com.mexcorgo.datamodel.*;
import com.mexcorgo.dto.request.NetSearchingRequest;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.exception.ResourceNotFoundException;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.NetSearchingRepository;
import com.mexcorgo.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NetSearchingService {

    private ToDoRepository toDoRepository;

    private NetSearchingRepository netSearchingRepository;

    @Autowired
    public NetSearchingService(ToDoRepository toDoRepository, NetSearchingRepository netSearchingRepository) {
        this.toDoRepository = toDoRepository;
        this.netSearchingRepository = netSearchingRepository;
    }

    public List<NetSearching> addNetSearching(Integer counter, List<NetSearchingRequest> netSearchingRequestList, User currentUser){

        Optional<ToDo> existingToDo = toDoRepository.findByToDoDateAndUser_UserId(LocalDate.now(), currentUser.getUserId());

        if(existingToDo.isPresent()){
            ToDo getToDo = existingToDo.get();
            if(getToDo.getNetSerchingCount() !=0){
                List<NetSearching> getListOfNetSearching = netSearchingRepository.findByToDo_ToDoId(existingToDo.get().getToDoId());

                List<NetSearching> newNetSearchingList = netSearchingRequestList.stream()
                        .map(netSearchingRequest -> {
                            NetSearching netSearching = new NetSearching();
                            netSearching = convertNetSearchingRequestToNetSearching(netSearching,netSearchingRequest);
                            netSearching.setToDo(getToDo);
                            return netSearching;
                        }).toList();
                getToDo.setNetSerchingCount(counter);
                //merge old phone details to newPhoneDetails
                getListOfNetSearching.addAll(newNetSearchingList);

                netSearchingRepository.saveAll(getListOfNetSearching);

                ToDo saveTodo = toDoRepository.save(getToDo);

                List<NetSearching> saveNetSearchingList = saveTodo.getNetSearchings();
                return saveNetSearchingList;
            }

            getToDo.setNetSerchingCount(counter);
            List<NetSearching> netSearchingList = netSearchingRequestList.stream()
                    .map(netSearchingRequest -> {
                        NetSearching netSearching = new NetSearching();
                        netSearching = convertNetSearchingRequestToNetSearching(netSearching,netSearchingRequest);
                        netSearching.setToDo(getToDo);
                        return netSearching;
                    }).toList();

            netSearchingRepository.saveAll(netSearchingList);

            ToDo saveTodo =  toDoRepository.save(getToDo);

            List<NetSearching> saveNetSearchingList = saveTodo.getNetSearchings();

            return saveNetSearchingList;

        }

        ToDo newToDo = new ToDo();
        newToDo.setToDoDate(LocalDate.now());
        newToDo.setPhoneDetailsCount(0);
        newToDo.setEmailDetailsCount(0);
        newToDo.setMeetingCount(0);
        newToDo.setMiscellaneousCount(0);
        newToDo.setNetSerchingCount(counter);
        newToDo.setPhysicalMeetingCount(0);
        newToDo.setReportCount(0);
        newToDo.setWhatsAppDetailsCount(0);
        newToDo.setUser(currentUser);
        List<NetSearching> netSearchingList = netSearchingRequestList.stream()
                .map(netSearchingRequest -> {
                    NetSearching netSearching = new NetSearching();
                    netSearching = convertNetSearchingRequestToNetSearching(netSearching,netSearchingRequest);
                    netSearching.setToDo(newToDo);
                    return netSearching;
                }).toList();
        newToDo.setNetSearchings(netSearchingList);

        ToDo saveTodo = toDoRepository.save(newToDo);
        List<NetSearching> saveNetSearchingList = saveTodo.getNetSearchings();
        return saveNetSearchingList;
    }


    public List<NetSearching> getNetSearchingDetailsByToDoId(Long toDoId){
        List<NetSearching> existingNetSerachingDetails = netSearchingRepository.findByToDo_ToDoId(toDoId);
        return existingNetSerachingDetails;
    }


    public NetSearching updateNetSearchingDetails(Long netSerachingId,NetSearchingRequest netSearchingRequest){
        NetSearching existingNetSearching = netSearchingRepository.findById(netSerachingId)
                .orElseThrow(()-> new UserNotFoundException("net searching details with id : "+netSerachingId+" is not found"));

        existingNetSearching.setName(netSearchingRequest.getName());
        existingNetSearching.setTime(netSearchingRequest.getTime());

        NetSearching saveNetSearchingDetails = netSearchingRepository.save(existingNetSearching);

        return saveNetSearchingDetails;
    }


    public NetSearching getNetSearchingDetailsById(Long netSearchingId){
        NetSearching netSearchingDetails = netSearchingRepository.findById(netSearchingId)
                .orElseThrow(()-> new UserNotFoundException("net searching details with id : "+netSearchingId+" is not found"));

        return netSearchingDetails;
    }

    public String deleteNetSearchingById(Long netSearchingId,Long toDoId){
        ToDo existingToDo = toDoRepository.findById(toDoId)
                .orElseThrow(()-> new ResourceNotFoundException("to do is not found with id : "+toDoId));
        netSearchingRepository.deleteById(netSearchingId);
        existingToDo.setNetSerchingCount(existingToDo.getNetSerchingCount()-1);
        toDoRepository.save(existingToDo);
        return "Net Searching is deleted suceefully";
    }

    private NetSearching convertNetSearchingRequestToNetSearching( NetSearching netSearching,NetSearchingRequest netSearchingRequest){
        netSearching.setName(netSearchingRequest.getName());
        netSearching.setTime(netSearchingRequest.getTime());
        return netSearching;
    }

}
