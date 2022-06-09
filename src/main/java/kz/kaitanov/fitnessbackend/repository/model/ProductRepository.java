package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
            FROM Product p
            WHERE p.name = :name
            """)
    boolean existsByName(String name);

    Optional<Product> findByName(String name);
}
