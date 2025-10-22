package com.mexcorgo.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GroupRequest {

    @NotBlank(message = "group name must not be null")
    private String groupName;

    @NotBlank(message = "member must not be null")
    private Set<MemberRequest> members;

    @NotBlank(message = "leader must not be null")
    private Long leaderId;
}
