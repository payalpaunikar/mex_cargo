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
public class Master {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long masterId;

       private String associateCode;

       private String serviceSector;

       private String companyName;

       private String contactName;

       private String contactNumber;

       private String emailId;

       private Integer grade;

       private String location;

       private String hub;

       private String state;

       @OneToMany(mappedBy = "master", cascade = CascadeType.ALL)
       private Set<QuatationMaster> receivedQuotations = new HashSet<>();
}
