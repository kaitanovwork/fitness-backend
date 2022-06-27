package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RecipeService extends AbstractService<Recipe, Long> {

    boolean existsByName(String name);

    Recipe addProductToRecipe(Recipe recipe, Product product);

    Recipe deleteProductFromRecipe(Recipe recipe, Product product);

    Optional<Recipe> findByIdWithProducts(Long id);

    Page<Recipe> findAll(Pageable pageable);
}
