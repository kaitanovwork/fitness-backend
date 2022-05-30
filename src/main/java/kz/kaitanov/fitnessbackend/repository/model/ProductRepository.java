package kz.kaitanov.fitnessbackend.repository.model;
import kz.kaitanov.fitnessbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    Optional<Product> findByName(String name);

}

