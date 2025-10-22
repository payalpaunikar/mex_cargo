package com.mexcorgo.service;


import com.mexcorgo.datamodel.Department;
import com.mexcorgo.datamodel.Role;
import com.mexcorgo.datamodel.User;
import com.mexcorgo.dto.request.LoginRequest;
import com.mexcorgo.dto.response.LoginResponse;
import com.mexcorgo.repository.UserRepository;
import com.mexcorgo.security.JWTService;
import com.mexcorgo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    private final   AuthenticationManager authenticationManager;

    private final JWTService jwtService;

    @Autowired
    public LoginService(UserRepository userRepository,AuthenticationManager authenticationManager,JWTService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {

            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

            User getUser = userDetails.getUser();

            String token = jwtService.generateToken(userDetails);

//            User getUser = userRepository.getByEmail(userDetails.getUsername());

            Role getUserRole = getUser.getRole();

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserId(getUser.getUserId());
            loginResponse.setEmail(getUser.getEmail());
            loginResponse.setRoleId(getUserRole.getRoleId());
            loginResponse.setRoleName(getUserRole.getRoleName());

            if(!getUserRole.getRoleName().equals("Admin")){
                Department getUserDepartment = getUser.getDepartment();
                loginResponse.setDepartmentId(getUserDepartment.getDepartmentId());
                loginResponse.setDepartmentName(getUserDepartment.getDepartmentName());
            }

            loginResponse.setToken(token);

            return loginResponse;
        }

        throw new RuntimeException("User creadential is invalid");

    }
}
