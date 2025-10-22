package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProjectTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private LocalDate idealDate;
    private LocalTime idealTime;
    private LocalDate implementedDate;
    private LocalTime implementedTime;
    private Long timeDifferenceMinutes;

    @ManyToOne
    @JoinColumn(name = "quatation_project_executive_id")
    @JsonIgnore
    private QuatationProjectExecutive quatationProjectExecutive;

    @PrePersist
    @PreUpdate
    public void calculateTimeDifference() {
        if (idealDate != null && idealTime != null && implementedDate != null && implementedTime != null) {
            LocalDateTime idealDateTime = LocalDateTime.of(idealDate, idealTime);
            LocalDateTime implementedDateTime = LocalDateTime.of(implementedDate, implementedTime);
            this.timeDifferenceMinutes = Duration.between(idealDateTime, implementedDateTime).toMinutes();
        }
    }
}
