package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private String email;
    private Long roleId;
    private String roleName;
    private Long departmentId;
    private String departmentName;
    private String token;
}
