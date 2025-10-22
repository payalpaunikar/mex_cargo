package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(nullable = false)
    private String roleName;


    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

}
