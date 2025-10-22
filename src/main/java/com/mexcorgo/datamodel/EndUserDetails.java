package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EndUserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long endUserId;
    private String userName;
    private String department;
    private String designation;
    private String contactNo;
    private String landLineNo;
    private String mailId;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Lead lead;
}
