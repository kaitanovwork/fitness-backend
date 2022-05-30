package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.repository.model.RecipeRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeServiceImpl extends AbstractServiceImpl<Recipe, Long> implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAllByName(String name) {
        return recipeRepository.findAllByName(name);
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        Integer calorieSum =  recipe.getProducts().stream().mapToInt(Product::getCalorie).sum();
        recipe.setCalorie(calorieSum);
        return recipeRepository.save(recipe);
    }

    @Override
    @Transactional
    public Recipe update (Recipe recipe){
        Integer calorieUpdateSum =  recipe.getProducts().stream().mapToInt(Product::getCalorie).sum();
        recipe.setCalorie(calorieUpdateSum);
        return recipeRepository.save(recipe);
    }
}