package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
@Entity
public class SalesAndMarketingTasks {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long salesAndMarketingTaskId;

    private String name;

    private String description;

    private Boolean isCompleted = false;

    private LocalDate completedTaskDate;

    private LocalTime completedTaskTime;


    // Only used for "Agreement Paper Sent" and "Agreement Confirmation"
    private String agreementNumber;

    // Only used for "Agreement Confirmation" (e.g., Email, WhatsApp, etc.)
    private String confirmationMode;

    @ManyToOne
    @JsonIgnore
    private Lead lead;

    // Constructor to simplify task creation
    public SalesAndMarketingTasks(String name, String description, Lead lead) {
        this.name = name;
        this.description = description;
        this.lead = lead;
        this.isCompleted = false;
    }

    public SalesAndMarketingTasks() {}
}
