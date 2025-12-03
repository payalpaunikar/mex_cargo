package com.mexcorgo.mapper;

import com.mexcorgo.datamodel.Need;
import com.mexcorgo.dto.request.NeedRequest;
import com.mexcorgo.dto.response.NeedResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NeedMapper {

    Need converNeedResponseDtoIntoNeed(NeedResponseDto needResponseDto);

    Need convertNeedRequestIntoNeed(NeedRequest needRequest);
}
