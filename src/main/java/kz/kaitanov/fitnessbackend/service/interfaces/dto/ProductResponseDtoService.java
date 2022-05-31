package kz.kaitanov.fitnessbackend.service.interfaces.dto;

import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductResponseDtoService {

    List<ProductResponseDto> findAll();

    Optional<ProductResponseDto> findById(Long id);

    Optional<ProductResponseDto> findByName(String name);
}

