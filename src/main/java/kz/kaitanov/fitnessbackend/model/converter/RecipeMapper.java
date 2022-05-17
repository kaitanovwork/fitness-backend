package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.dto.request.RecipePersistRequestDto;

public final class RecipeMapper {

    public static Recipe toEntity(RecipePersistRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setCalorie(dto.calorie());
        recipe.setProtein(dto.protein());
        recipe.setFat(dto.fat());
        recipe.setCarbohydrate(dto.carbohydrate());
        return recipe;
    }
}
