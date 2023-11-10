package com.stefan.security.service.impl;

import com.stefan.security.dto.CarDTO;
import com.stefan.security.entity.BrandEntity;
import com.stefan.security.entity.CarEntity;
import com.stefan.security.exception.NotFoundException;
import com.stefan.security.mapper.ICarMapper;
import com.stefan.security.repository.BrandEntityRepository;
import com.stefan.security.repository.CarEntityRepository;
import com.stefan.security.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarEntityRepository carEntityRepository;
    private final BrandEntityRepository brandEntityRepository;
    private final ICarMapper carMapper;

    @Override
    public void save(CarDTO carDTO) {
        CarEntity carEntity = carMapper.fromCarDTOToCarEntity(carDTO);
        BrandEntity brandEntity = brandEntityRepository.findByName(carDTO.brand())
                .orElse(new BrandEntity(carDTO.brand()));
        carEntity.setBrand(brandEntity);

        carEntityRepository.save(carEntity);
    }

    @Override
    public List<CarDTO> findAll() {
        return carEntityRepository.findAll().stream()
                .map(carMapper::fromCarEntityToCarDTO)
                .toList();
    }

    @Override
    public CarDTO findById(Long id) {
        return carMapper.fromCarEntityToCarDTO(carEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Car not found by id " + id)));
    }

    @Override
    public void delete(Long id) {
        carEntityRepository.deleteById(id);
    }
}
