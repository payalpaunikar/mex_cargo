package com.mexcorgo.mapper;

import com.mexcorgo.datamodel.Company;
import com.mexcorgo.dto.response.CompanyResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company convertCompanyReponseDtoIntoCompany(CompanyResponseDto companyResponseDto);
}
