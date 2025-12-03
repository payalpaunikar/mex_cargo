package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;
    private String companyName;
    private String companySector;
    private String companySetUp;
    private String headOfOffice;
    private String state;
    private String easyHubCentre;
    private String minorHub;
    private String majorHub;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Lead lead;
}
