package com.mexcorgo.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdditionalNeedRequestWithId {
    private Long additionalNeedId;
    private String needName;
    private String needValue;
}
