package com.mexcorgo.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDetailsWhichWeTransferToAccountExcutiveDto {
    private String leadReferenceNo;
    private String userName;
    private String contactNo;
    private String mailId;
}
