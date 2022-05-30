package kz.kaitanov.fitnessbackend.repository.model;
import kz.kaitanov.fitnessbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

}

