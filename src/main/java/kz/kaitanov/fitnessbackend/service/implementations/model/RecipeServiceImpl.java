package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.repository.model.RecipeRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl extends AbstractServiceImpl<Recipe, Long> implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return recipeRepository.existsByName(name);
    }

    @Override
    public Recipe addProductToRecipe(Recipe recipe, Product product) {
        recipe.addProduct(product);
        calcCalorie(recipe);
        return update(recipe);
    }

    @Override
    public Recipe deleteProductFromRecipe(Recipe recipe, Product product) {
        recipe.deleteProduct(product);
        calcCalorie(recipe);
        return update(recipe);
    }

    @Override
    public Optional<Recipe> findByIdWithProducts(Long id) {
        return recipeRepository.findByIdWithProducts(id);
    }

    private void calcCalorie(Recipe recipe) {
        recipe.setCalorie(recipe.getProducts().stream().mapToInt(Product::getCalorie).sum());
        recipe.setProtein(recipe.getProducts().stream().mapToInt(Product::getProtein).sum());
        recipe.setFat(recipe.getProducts().stream().mapToInt(Product::getFat).sum());
        recipe.setCarbohydrate(recipe.getProducts().stream().mapToInt(Product::getCarbohydrate).sum());
    }

    @Override
    public Page<Recipe> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    @Override
    public Recipe addProductsToRecipe(Recipe recipe, List<Product> products) {
        for (Product product : products) {
            recipe.addProduct(product);
        }
        calcCalorie(recipe);
        return update(recipe);
    }

    public Recipe deleteProductsFromRecipe(Recipe recipe, List<Product> products) {
        for (Product product : products) {
            recipe.deleteProduct(product);
        }
        calcCalorie(recipe);
        return update(recipe);
    }
}
