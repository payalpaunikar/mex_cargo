package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupTeamEntityResponse {
    private Long id;
    private String name;
    private String email;
    private String number;
    private String role;
}
