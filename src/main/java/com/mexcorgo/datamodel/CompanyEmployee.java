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
public class CompanyEmployee {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long companyEmployeeId;
     private String employeeName;
     private String department;
     private String designation;
     private String contactNo;
     private String landLineNo;
     private String mailId;

     @OneToOne(fetch = FetchType.LAZY)
     @JsonIgnore
     private Lead lead;
}
