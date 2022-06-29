package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService extends AbstractService<Product, Long> {

    boolean existsByName(String name);

    Optional<Product> findByName(String name);

    List<Product> findById(Long[] id);
}
