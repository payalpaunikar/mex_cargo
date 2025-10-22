package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupResponse {
    private Long groupId;
    private String groupName;
    private GroupTeamEntityResponse leader;
    private Set<GroupTeamEntityResponse> memberList;
}
