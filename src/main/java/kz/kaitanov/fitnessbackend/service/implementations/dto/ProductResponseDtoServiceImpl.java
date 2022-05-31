package kz.kaitanov.fitnessbackend.service.implementations.dto;

import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;
import kz.kaitanov.fitnessbackend.repository.dto.ProductResponseDtoRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.ProductResponseDtoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductResponseDtoServiceImpl implements ProductResponseDtoService {

    ProductResponseDtoRepository productResponseDtoRepository;

    public ProductResponseDtoServiceImpl(
            ProductResponseDtoRepository productResponseDtoRepository) {
        this.productResponseDtoRepository = productResponseDtoRepository;
    }

    @Override
    public List<ProductResponseDto> findAll() {
        return productResponseDtoRepository.getProductResponseDtoList();
    }

    @Override
    public Optional<ProductResponseDto> findById(Long productId) {
        return productResponseDtoRepository.getProductResponseDtoById(productId);
    }

    @Override
    public Optional<ProductResponseDto> findByName(String name) {
        return productResponseDtoRepository.getProductResponseDtoByName(name);
    }
}
