package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EmailDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emailId;
    private String name;
    private String email;
    private LocalTime emailTime;

    @ManyToOne
    @JoinColumn(name = "to_do_id")
    @JsonIgnore
    private ToDo toDo;

}
