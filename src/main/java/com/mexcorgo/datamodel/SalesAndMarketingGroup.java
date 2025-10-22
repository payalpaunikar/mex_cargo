package com.mexcorgo.datamodel;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SalesAndMarketingGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false,unique = true)
    private String groupName;

    @OneToMany
    @JoinTable(name = "group_member",
    joinColumns = @JoinColumn(name = "group_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> member;

    @OneToOne
    @JoinColumn(name = "leader_id",unique = true,nullable = false)
    private User leader;
}
