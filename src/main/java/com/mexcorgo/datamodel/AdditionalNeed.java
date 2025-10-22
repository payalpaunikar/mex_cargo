package com.mexcorgo.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class AdditionalNeed {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long additionalNeedId;

      private String articleName;

      private String articleValue;

      private String articleDimension;

      @ManyToOne(fetch = FetchType.LAZY)
      @JoinColumn(name = "need_id")
      @JsonIgnore
      private Need need;
}
