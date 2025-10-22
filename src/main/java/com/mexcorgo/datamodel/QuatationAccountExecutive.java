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
public class QuatationAccountExecutive {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long qutationAccountExecutiveId;

    @ManyToOne
    @JoinColumn(name = "quatation_id", nullable = false)
    private Quatation quatation;

    @ManyToOne
    @JoinColumn(name = "account_executive_id",nullable = false)
    private User accountExecutive;

    private LocalDate quatationForwardDate;

    private LocalTime quatationForwardTime;

    // ✅ These are your new fields – for service data tracking
    private Boolean isServiceDataForwarded;

    private LocalDate serviceForwardedDate;
    private LocalTime serviceForwardedTime;
}
