package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "todo",indexes = {
        @Index(name = "idx_todo_user_id_date",columnList = "user_user_id,toDoDate")
})
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toDoId;

    @Column(nullable = false)
    private LocalDate toDoDate;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer phoneDetailsCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer emailDetailsCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer whatsAppDetailsCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer NetSerchingCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer PhysicalMeetingCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer reportCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer meetingCount;

    @Column(nullable = false,columnDefinition = "int default 0")
    private Integer miscellaneousCount;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> reports;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Meeting> meetings;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Miscellaneous> miscellaneousTasks;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneDetails> phoneDetails;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmailDetails> emailDetails;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WhatsAppDetails> whatsAppDetails;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NetSearching> netSearchings;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhysicalMeeting> physicalMeetings;


     @ManyToOne
     private User user;
}
