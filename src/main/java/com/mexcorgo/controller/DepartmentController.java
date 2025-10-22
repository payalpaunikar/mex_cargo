package com.mexcorgo.controller;


import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.DepartmentWithRoleDto;
import com.mexcorgo.security.MyUserDetails;
import com.mexcorgo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/department")
    @PreAuthorize("hasAnyRole('Admin','Head')")
    public ResponseEntity<List<DepartmentWithRoleDto>> getAllDepartmentWithRole(Authentication authentication){
        User currentUser= ((MyUserDetails) authentication.getPrincipal()).getUser();
        return ResponseEntity.ok(departmentService.getAllDepartmentsWithRoles(currentUser));
    }

}
