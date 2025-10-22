package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentWithRoleDto {

    private Long departmentId;
    private String departmentName;
    List<RoleDto> roleList;
}
