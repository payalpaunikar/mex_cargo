package com.mexcorgo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalNeedRequest {
    private String articleName;
    private String articleValue;
    private String articleDimension;
    private String articleWeight;
}
