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
public class NetSearching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long netSearchingId;

    private String name;

    private LocalTime time;

    @ManyToOne
    @JoinColumn(name = "to_do_id")
    @JsonIgnore
    private ToDo toDo;

}
