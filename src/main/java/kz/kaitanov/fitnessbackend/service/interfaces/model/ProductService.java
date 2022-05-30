package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Product;

public interface ProductService extends AbstractService<Product, Long> {

    Product findByName(String name);
}



