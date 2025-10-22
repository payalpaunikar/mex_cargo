package com.mexcorgo.controller;


import com.mexcorgo.component.ToDoEntity;
import com.mexcorgo.datamodel.PhoneDetails;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.PhoneDetailsRequest;
import com.mexcorgo.dto.response.ToDoEntityCountResponse;
import com.mexcorgo.dto.response.ToDoResponseDto;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.ToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ToDoController {

       private ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }


    @GetMapping("/get/todoentity/{toDoEntity}/currentdate/count")
    @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
    public ToDoEntityCountResponse getToDoEntityCurrentDateCount(Authentication authentication, @PathVariable("toDoEntity") ToDoEntity toDoEntity){
        User currentUser = ((MyUserDetails) authentication.getPrincipal()).getUser();
      return toDoService.getEntityCount(currentUser.getUserId(), LocalDate.now(),toDoEntity);
    }


     @GetMapping("/get/todoentity/count/list")
     @PreAuthorize("hasAnyRole('Admin','Head','Leader','Member')")
     public Page<ToDoResponseDto> getToDoEntityCountList(
             @RequestParam Long userId,
             @RequestParam(value = "page",defaultValue = "0") int page,
             @RequestParam(value = "size",defaultValue = "10") int size
     ){
         return toDoService.getToDoEntityCountList(userId,page,size);
     }
}
