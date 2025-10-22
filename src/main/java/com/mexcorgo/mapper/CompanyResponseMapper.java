package com.mexcorgo.mapper;

import com.mexcorgo.datamodel.Company;
import com.mexcorgo.dto.response.CompanyResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyResponseMapper {

//    public abstract CompanyResponseDto toCompanyResponseDto(Company company);
    CompanyResponseDto toCompanyResponseDto(Company company);


}
