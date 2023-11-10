package com.stefan.security.mapper;

import com.stefan.security.dto.CarDTO;
import com.stefan.security.entity.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ICarMapper {

    @Mapping(target = "year", source = "yearOfManufacture")
    @Mapping(target = "brand", source = "brand.name")
    CarDTO fromCarEntityToCarDTO(CarEntity carEntity);

    @Mapping(target = "yearOfManufacture", source = "year")
    @Mapping(target = "brand", ignore = true)
    CarEntity fromCarDTOToCarEntity(CarDTO carDTO);
}
