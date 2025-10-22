package com.mexcorgo.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRequest {


    @NotBlank(message = "user name can not be null.")
    private String userName;

    @NotBlank(message = "email can not be null or blank.")
    @Email(message = "provide valid email id.")
    private String emailId;

    private String mobileNumber;

    @NotBlank(message = "password can not be null or blank.")
    private String password;

    @NotBlank(message = "department can not be null or blank.")
    private Long departmentId;

    @NotBlank(message = "role can not be null or blank.")
    private Long roleId;

}
