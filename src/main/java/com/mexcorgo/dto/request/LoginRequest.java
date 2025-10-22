package com.mexcorgo.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "email can not be null and blank.")
    @Email(message = "provide valid email.")
    private String email;

    @NotBlank(message = "password can not be null and blank.")
    private String password;

}
