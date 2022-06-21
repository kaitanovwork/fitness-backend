package kz.kaitanov.fitnessbackend.repository.model;

import kz.kaitanov.fitnessbackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("""
            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
            FROM Recipe r
            WHERE r.name = :name
            """)
    boolean existsByName(String name);

    @Query("""
            SELECT r
            FROM Recipe r LEFT JOIN FETCH r.products
            WHERE r.id = :id
            """)
    Optional<Recipe> findByIdWithProducts(Long id);
}
