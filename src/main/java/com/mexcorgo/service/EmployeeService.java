package com.mexcorgo.service;


import com.mexcorgo.component.EmployeeStatus;
import com.mexcorgo.datamodel.Department;
import com.mexcorgo.datamodel.Role;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.EmployeeRequest;
import com.mexcorgo.dto.response.EmployeeDetailsDescriptionResponse;
import com.mexcorgo.dto.response.EmployeeResponse;
import com.mexcorgo.dto.response.GetAndUpdateEmployeeDto;
import com.mexcorgo.exception.*;
import com.mexcorgo.repository.DepartmentRepository;
import com.mexcorgo.repository.RoleRepository;
import com.mexcorgo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
public class EmployeeService {

      private UserRepository userRepository;

      private RoleRepository roleRepository;

      private DepartmentRepository departmentRepository;

      private PasswordEncoder passwordEncoder;


      Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    public EmployeeService(UserRepository userRepository, RoleRepository roleRepository,
                           DepartmentRepository departmentRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public EmployeeResponse registerEmployee(EmployeeRequest employeeRequest, User currentUser) {

        Role role = roleRepository.findById(employeeRequest.getRoleId())
                .orElseThrow(()-> new UserNotFoundException("role with id : "+employeeRequest.getRoleId() + " is not fount"));


        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(()-> new UserNotFoundException("department with : "+employeeRequest.getDepartmentId()+" id is not found"));


        //we check the current user is admin -> admin have authority to create all type of employee
        if(currentUser.getRole().getRoleName().equalsIgnoreCase("Admin")) {

            //we check that admin is try to create the head of department
            if ("head".equalsIgnoreCase(role.getRoleName())) {

                // check if head is already exit in department -> each department have only one head
                if (userRepository.headOfDepartmentExit(role.getRoleName(), department.getDepartmentName())) {

                    throw new HeadAlredyExitException("A head for "+department.getDepartmentName()+" alredy exit. so, for each department" +
                            " we only register one head");
                }

               //created the new head because the department doesn't  have head
               User newUser = saveUser(employeeRequest,role,department);

                EmployeeResponse newEmployee = new EmployeeResponse();
                newEmployee = convertUserToEmployeeResponse(newUser,newEmployee);

                return newEmployee;
            }

            // admin is created the employee -> expect the head
           User newUser = saveUser(employeeRequest,role,department);
            EmployeeResponse newEmployee = new EmployeeResponse();
            newEmployee = convertUserToEmployeeResponse(newUser,newEmployee);

            return newEmployee;
        }

        // current user is head -> head have authority to created employee within their department
        if (currentUser.getRole().getRoleName().equalsIgnoreCase("Head")){

            // check if head created employee in their department
            if(currentUser.getDepartment().getDepartmentId().equals(employeeRequest.getDepartmentId())){
            User newUser = saveUser(employeeRequest,role,department);
            EmployeeResponse newEmployee = new EmployeeResponse();
            newEmployee = convertUserToEmployeeResponse(newUser,newEmployee);
                return newEmployee;
            }

            throw new UnauthorizedDepartmentAccessException("Head can not register another departemnet user");
        }

        throw new UnauthorizedException("You do not have permission to register the employee");

    }



    public List<EmployeeResponse> getListOfAvailableMember(){
        List<User> availableMemberList = userRepository.findAvailableMembers();

        List<EmployeeResponse> employeeResponsesList = availableMemberList.stream()
                .map(member-> {
                    EmployeeResponse newEmployeeResponse = new EmployeeResponse();
                    newEmployeeResponse = convertUserToEmployeeResponse(member,newEmployeeResponse);
                    return newEmployeeResponse;
                }).toList();

        return employeeResponsesList;
    }


    public List<EmployeeResponse> getListOfAvailableLeaders(){
        List<User> availableLeaders = userRepository.findAvailableLeaders();

        List<EmployeeResponse> employeeResponsesList = availableLeaders.stream()
                .map(leader-> {
                    EmployeeResponse newEmployeeResponse = new EmployeeResponse();
                    newEmployeeResponse = convertUserToEmployeeResponse(leader,newEmployeeResponse);
                    return newEmployeeResponse;
                }).toList();

        return employeeResponsesList;
    }




    private User saveUser(EmployeeRequest employeeRequest, Role role, Department department){
        User newUser = new User();
        newUser.setUserName(employeeRequest.getUserName());
        newUser.setEmail(employeeRequest.getEmailId());
        newUser.setMobileNumber(employeeRequest.getMobileNumber());
        newUser.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        newUser.setRole(role);
        newUser.setDepartment(department);
        newUser.setEmployeeStatus(EmployeeStatus.WORKING);
       User saveUser = userRepository.save(newUser);
       return saveUser;
    }

    private EmployeeResponse convertUserToEmployeeResponse(User user,EmployeeResponse employeeResponse){
        employeeResponse.setUserId(user.getUserId());
        employeeResponse.setUserName(user.getUserName());
        employeeResponse.setEmailId(user.getEmail());
        employeeResponse.setMobileNo(user.getMobileNumber());
        employeeResponse.setRoleId(user.getRole().getRoleId());
        employeeResponse.setDepartmentId(user.getDepartment().getDepartmentId());
        employeeResponse.setEmployeeStatus(user.getEmployeeStatus());

        return employeeResponse;
    }


    public List<EmployeeDetailsDescriptionResponse> getEmployeeListAccordingFilter(String departmentName,String roleName,
                                                                                   User currentUser){

        Department department = null;
        Role role = null;

        if (roleName!=null && !roleName.isBlank()){
            role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(()-> new ResourceNotFoundException("Role not found with name : "+roleName));
        }


        if (currentUser.getRole().getRoleName().equalsIgnoreCase("Admin")){

            if(departmentName != null && !departmentName.isBlank()) {
                department = departmentRepository.findByDepartmentNameIgnoreCase(departmentName)
                        .orElseThrow(()-> new ResourceNotFoundException("Department  not found with name : "+departmentName));
            }


            List<User> users = userRepository.findEmployeeByFilters(department,role);

            List<EmployeeDetailsDescriptionResponse> employeeDetailsDescriptionResponses = users.stream()
                    .filter(user -> user.getDepartment() !=null)
                    .map(user -> {
                        EmployeeDetailsDescriptionResponse employeeDetailsDescriptionResponse = new EmployeeDetailsDescriptionResponse();
                        employeeDetailsDescriptionResponse.setUserId(user.getUserId());
                        employeeDetailsDescriptionResponse.setDepartmentName(user.getDepartment().getDepartmentName());
                        employeeDetailsDescriptionResponse.setRoleName(user.getRole().getRoleName());
                        employeeDetailsDescriptionResponse.setUserName(user.getUserName());
                        employeeDetailsDescriptionResponse.setEmailId(user.getEmail());
                        employeeDetailsDescriptionResponse.setMobileNo(user.getMobileNumber());
                        employeeDetailsDescriptionResponse.setEmployeeStatus(user.getEmployeeStatus());
                        return employeeDetailsDescriptionResponse;
                    }).toList();

            return employeeDetailsDescriptionResponses;
        }

        if (currentUser.getRole().getRoleName().equalsIgnoreCase("Head")){
            department = currentUser.getDepartment();
            List<User> users = userRepository.findEmployeeByFilters(department,role);

            List<EmployeeDetailsDescriptionResponse> employeeDetailsDescriptionResponses = users.stream()
                    .filter(user ->  !user.getRole().getRoleName().equals(currentUser.getRole().getRoleName()))
                    .map(user -> {
                        EmployeeDetailsDescriptionResponse employeeDetailsDescriptionResponse = new EmployeeDetailsDescriptionResponse();
                        employeeDetailsDescriptionResponse.setUserId(user.getUserId());
                        employeeDetailsDescriptionResponse.setDepartmentName(user.getDepartment().getDepartmentName());
                        employeeDetailsDescriptionResponse.setRoleName(user.getRole().getRoleName());
                        employeeDetailsDescriptionResponse.setUserName(user.getUserName());
                        employeeDetailsDescriptionResponse.setEmailId(user.getEmail());
                        employeeDetailsDescriptionResponse.setMobileNo(user.getMobileNumber());
                        employeeDetailsDescriptionResponse.setEmployeeStatus(user.getEmployeeStatus());
                        return employeeDetailsDescriptionResponse;
                    }).toList();

            return employeeDetailsDescriptionResponses;

        }

         throw new UnauthorizedException("You doesn't have authority to access this resources");
    }


    public GetAndUpdateEmployeeDto getEmployeeById(Long empId){
        User user = userRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+empId));

        GetAndUpdateEmployeeDto getAndUpdateEmployeeDto = new GetAndUpdateEmployeeDto();
        getAndUpdateEmployeeDto.setUserId(user.getUserId());
        getAndUpdateEmployeeDto.setEmailId(user.getEmail());
        getAndUpdateEmployeeDto.setMobileNo(user.getMobileNumber());
        getAndUpdateEmployeeDto.setUserName(user.getUserName());
        getAndUpdateEmployeeDto.setEmployeeStatus(user.getEmployeeStatus());

        return getAndUpdateEmployeeDto;
    }


    public GetAndUpdateEmployeeDto updateEmployeeDto(Long empId,GetAndUpdateEmployeeDto getAndUpdateEmployeeDto){
        User user = userRepository.findById(empId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found with id : "+empId));

        user.setUserName(getAndUpdateEmployeeDto.getUserName());
        user.setEmail(getAndUpdateEmployeeDto.getEmailId());
        user.setMobileNumber(getAndUpdateEmployeeDto.getMobileNo());
        user.setEmployeeStatus(getAndUpdateEmployeeDto.getEmployeeStatus());

        userRepository.save(user);

        GetAndUpdateEmployeeDto updateEmployeeDto = new GetAndUpdateEmployeeDto();
        updateEmployeeDto.setUserId(user.getUserId());
        updateEmployeeDto.setEmailId(user.getEmail());
        updateEmployeeDto.setMobileNo(user.getMobileNumber());
        updateEmployeeDto.setUserName(user.getUserName());
        updateEmployeeDto.setEmployeeStatus(user.getEmployeeStatus());

        return updateEmployeeDto;
    }



}
