package com.mexcorgo.datamodel;


import com.mexcorgo.component.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"quatation_id","service_type_id"}
))
public class PlanningSelectionMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quatation_id",nullable = false)
    private Quatation quatation;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "quatation_master_id",nullable = false)
    private QuatationMaster quatationMaster;


    private LocalDate selectedDate;

    private LocalTime selectedTime;

    @Column(nullable = false)
    private BigDecimal finalServiceAmount;

}
