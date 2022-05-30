package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.repository.model.ProductRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl extends AbstractServiceImpl<Product, Long> implements ProductService {

   private final ProductRepository productRepository;

    public ProductServiceImpl (ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
