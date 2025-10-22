package com.mexcorgo.datamodel;


import com.mexcorgo.component.QuatationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Quatation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quatationId;

    private String quatationReferenceNo;

    private LocalDate quatationRequiredDate;

    private LocalTime quatationRequiredTime;

    private LocalDate quatationReceivedPricingDate;

    private LocalTime quatationReceivedPricingTime;

    private LocalDate quatationForwardedDateToPurchaseExecutive;

    private LocalTime quatationForwardedTimeToPurchaseExecutive;

    @Enumerated(EnumType.STRING)
    @Column(length = 100) // Set appropriate length
    private QuatationStatus quatationStatus;

    @ManyToMany
    @JoinTable(
            name = "quatation_purchase_executive",
            joinColumns = @JoinColumn(name = "quotation_id"),
            inverseJoinColumns = @JoinColumn(name = "purchase_executive_id")
    )
    private Set<User> referredToPurchaseExecutives = new HashSet<>(); // Multiple purchase executives


    @OneToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @OneToMany(mappedBy = "quatation", cascade = CascadeType.ALL)
    private Set<QuatationMaster> quotationMasters = new HashSet<>();


//    @ManyToOne
//    @JoinColumn(name = "finalized_master_response_id")
//    private QuatationMaster finalizedMasterResponse;

    private Double analyzePackageCost;

    private Double analyzeTrsCost;

    private Double analyzeCarServiceCost;

    private Double analyzeAdditionalServiceCost;

    @Column(nullable = false)
    private Boolean isParticularAmountAdded = false;

    @Column(nullable = false)
    private Boolean isSendToMaster = false;

    @Column(nullable = false)
    private Boolean isSendToPricing = false;

    @Column(nullable = false)
    private Boolean isAnalyzePricingDone = false;

    @Column(nullable = false)
    private Boolean isPricingSendToLeadCreator = false;

    @Column(nullable = false)
    private Boolean isSendToProject = false;

}
