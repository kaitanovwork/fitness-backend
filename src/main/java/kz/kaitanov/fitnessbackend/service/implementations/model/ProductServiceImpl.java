package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.converter.ProductMapper;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.repository.model.ProductRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl extends AbstractServiceImpl<Product, Long> implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Page<ProductResponseDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper::toDto);
    }

}
