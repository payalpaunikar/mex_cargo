package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mexcorgo.component.FollowUpStatus;
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
public class FollowUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followUpId;

    private LocalDate followUpDate;

    private String followUpRemark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    @JsonIgnore
    private Note note;

    @ManyToOne
    @JsonIgnore
    private User user;
}
