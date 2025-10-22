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
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Note {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long noteId;

      private String remark;

      private LocalDate date;

      private LocalTime time;

      private Integer rating;

      @OneToMany(mappedBy = "note",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
      private List<FollowUp> followUps;

      @Enumerated(EnumType.STRING)
      private FollowUpStatus followUpStatus;

      @OneToOne(fetch = FetchType.LAZY)
      @JsonIgnore
      private Lead lead;
}
