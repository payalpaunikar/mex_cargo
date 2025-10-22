package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class QuatationMaster {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long quatationMasterId;

    @ManyToOne
    @JoinColumn(name = "quotation_id", nullable = false)
    private Quatation quatation;  // One Quotation → Multiple Masters

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = false)
    private Master master;  // One Master → Multiple Quotations

    private LocalDate quatationForwardDate;

    private LocalTime quatationForwardTime;

}
