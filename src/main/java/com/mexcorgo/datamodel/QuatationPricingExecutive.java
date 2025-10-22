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
public class QuatationPricingExecutive {

       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long quatationPricingExecutiveId;

       @ManyToOne
       @JoinColumn(name = "quotation_id", nullable = false)
       private Quatation quatation;  // One Quotation → Multiple Pricing Executive

      @ManyToOne
      @JoinColumn(name = "pricing_executive_id",nullable = false)
      private User pricing_executive_id;

       private LocalDate quatationForwardDate;

       private LocalTime quatationForwardTime;

}
