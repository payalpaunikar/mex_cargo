package com.mexcorgo.service;


import com.mexcorgo.datamodel.Department;
import com.mexcorgo.datamodel.Role;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.response.DepartmentWithRoleDto;
import com.mexcorgo.dto.response.RoleDto;
import com.mexcorgo.exception.UnauthorizedDepartmentAccessException;
import com.mexcorgo.repository.DepartmentRepository;
import com.mexcorgo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    private RoleRepository roleRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    public List<DepartmentWithRoleDto> getAllDepartmentsWithRoles(User currentUser){

        if(currentUser.getRole().getRoleName().equalsIgnoreCase("Admin")){
            List<Department> departmentList = departmentRepository.getAllDepartmentWithRoles();

            List<DepartmentWithRoleDto> departmentWithRoleDtoList = departmentList.stream()
                    .map(department -> {
                        DepartmentWithRoleDto departmentWithRoleDto = new DepartmentWithRoleDto();
                        departmentWithRoleDto.setDepartmentId(department.getDepartmentId());
                        departmentWithRoleDto.setDepartmentName(department.getDepartmentName());

                        List<RoleDto> roleDtoList = department.getRoles().stream()
                                .map(role -> {
                                    RoleDto newRoleDto = new RoleDto();
                                    newRoleDto.setRoleId(role.getRoleId());
                                    newRoleDto.setRoleName(role.getRoleName());
                                    return newRoleDto;
                                }).toList();

                        departmentWithRoleDto.setRoleList(roleDtoList);
                        return departmentWithRoleDto;
                    }).toList();

            return departmentWithRoleDtoList;
        }
        if (currentUser.getRole().getRoleName().equalsIgnoreCase("Head")){
           Department getDepartment = departmentRepository.getDepartmentWithRoles(currentUser.getDepartment().getDepartmentId());

           DepartmentWithRoleDto departmentWithRoleDto = new DepartmentWithRoleDto();

           departmentWithRoleDto.setDepartmentId(getDepartment.getDepartmentId());
           departmentWithRoleDto.setDepartmentName(getDepartment.getDepartmentName());

           List<RoleDto> roleDtoList = getDepartment.getRoles().stream()
                   .filter(role -> !role.getRoleName().equalsIgnoreCase("Head"))
                   .map(role -> {
                       RoleDto newRoleDto = new RoleDto();
                       newRoleDto.setRoleId(role.getRoleId());
                       newRoleDto.setRoleName(role.getRoleName());

                       return newRoleDto;
                   }).toList();

           departmentWithRoleDto.setRoleList(roleDtoList);

           return List.of(departmentWithRoleDto);
        }

        throw new UnauthorizedDepartmentAccessException("You do not have permission for get deaprtment with roles");
    }
}
