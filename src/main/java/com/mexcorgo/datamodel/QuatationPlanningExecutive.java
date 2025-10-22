package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class QuatationPlanningExecutive {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long quatationPlanningExecutiveId;

    @ManyToOne
    @JoinColumn(name = "quatation_id", nullable = false)
    private Quatation quatation;

    @ManyToOne
    @JoinColumn(name = "planning_executive_id",nullable = false)
    private User planningExceutive;

    private LocalDate quatationForwardDate;

    private LocalTime quatationForwardTime;

}
