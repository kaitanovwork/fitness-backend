package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Product;

import java.util.Optional;

public interface ProductService extends AbstractService<Product, Long> {

    Optional <Product> findByName(String name);
}



