package com.mexcorgo.mapper;

import com.mexcorgo.datamodel.CompanyEmployee;
import com.mexcorgo.dto.response.CompanyEmployeeResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyEmployeeResponseMapper {

    CompanyEmployeeResponseDto toCompanyEmployeeResponseDto(CompanyEmployee companyEmployee);
}
