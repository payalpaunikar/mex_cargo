package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    private String departmentName;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();


    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

}
