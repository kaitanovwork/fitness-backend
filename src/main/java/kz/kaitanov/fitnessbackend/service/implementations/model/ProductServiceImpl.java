package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.repository.model.ProductRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
    public List<Product> findByIds(Long[] id) {
        return productRepository.findAllById(Arrays.asList(id));
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

}
