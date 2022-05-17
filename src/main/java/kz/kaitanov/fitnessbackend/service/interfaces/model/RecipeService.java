package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Recipe;

import java.util.List;

public interface RecipeService extends AbstractService<Recipe, Long> {

    List<Recipe> findAllByName(String name);
}
