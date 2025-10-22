package com.mexcorgo.service;


import com.mexcorgo.datamodel.Department;
import com.mexcorgo.datamodel.Role;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.exception.UserNotFoundException;
import com.mexcorgo.repository.DepartmentRepository;
import com.mexcorgo.repository.RoleRepository;
import com.mexcorgo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyServices implements ApplicationRunner {

    @Value("${admin.name}")
    private String adminUserName;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${admin.mobileNumber}")
    private String mobileNumber;


    private Logger logger = LoggerFactory.getLogger(MyServices.class);


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private DepartmentRepository departmentRepository;

    @Autowired
    public MyServices(UserRepository userRepository, PasswordEncoder passwordEncoder,
                      RoleRepository roleRepository,DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(departmentRepository.count() <= 0){
            List<Department> departmentList = new ArrayList<>();
            departmentList.add(createdNewDepartment("Sales And Marketing"));
            departmentList.add(createdNewDepartment("Operation"));
            departmentList.add(createdNewDepartment("Account"));

            departmentRepository.saveAll(departmentList);

            logger.info("department is created in the table");

        }
        if (roleRepository.count() <=0){
            List<Department> getListOfDepartment = departmentRepository.findAll();

            List<Role> roleList = new ArrayList<>();

            for (Department department : getListOfDepartment){
               if (department.getDepartmentName().equals("Sales And Marketing")){
                   roleList.add(createNewRole("Head",department));
                   roleList.add(createNewRole("Leader",department));
                   roleList.add(createNewRole("Member",department));

                   roleRepository.saveAll(roleList);

                   logger.info("roles for sales and marketing department is created");
               }
               if (department.getDepartmentName().equals("Operation")){
                   roleList.add(createNewRole("Head",department));
                   roleList.add(createNewRole("Purchase Executive",department));
                   roleList.add(createNewRole("Pricing Executive",department));
                   roleList.add(createNewRole("Planning Executive",department));
                   roleList.add(createNewRole("Project Executive",department));
                   roleList.add(createNewRole("Customer Relations Executive",department));
                   roleRepository.saveAll(roleList);

                   logger.info("roles for operation department is created");
               }
               if (department.getDepartmentName().equals("Account")){
                    roleList.add(createNewRole("Head",department));
                   roleRepository.saveAll(roleList);
                   logger.info("roles for account department is created");
               }

            }

            roleRepository.save(createNewRole("Admin",null));
        }

        if (userRepository.count() <=0){

            Role existingRole = roleRepository.findByRoleName("Admin")
                    .orElseThrow(()-> new UserNotFoundException("Role with Admin name is not found"));

            User adminUser = new User();
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setUserName(adminUserName);
            adminUser.setMobileNumber(mobileNumber);
            adminUser.setRole(existingRole);


            User newAdminUser = userRepository.save(adminUser);

            logger.info("admin email : "+newAdminUser.getEmail()+" save succefully");
        }


    }

   public Role createNewRole(String roleName,Department department){
        Role newRole = new Role();
        newRole.setRoleName(roleName);
        newRole.setDepartment(department);
        return newRole;
   }


   private Department createdNewDepartment(String departmentName){
        Department newDepartment = new Department();
        newDepartment.setDepartmentName(departmentName);
        return newDepartment;
   }

}
