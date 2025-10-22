package com.mexcorgo.mapper;

import com.mexcorgo.datamodel.Need;
import com.mexcorgo.datamodel.NeedExtraDetails;
import com.mexcorgo.dto.request.NeedExtraDetailsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NeedExtraDetailsMapper {

    NeedExtraDetails convertNeedExtraDetailsDtoIntoNeedExtraDetails(NeedExtraDetailsDto needExtraDetailsDto);
}
