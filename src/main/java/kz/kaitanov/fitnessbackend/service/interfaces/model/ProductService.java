package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService extends AbstractService<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    Page<ProductResponseDto> findAll(Pageable pageable);
}
