package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.RecipeResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;

public final class RecipeMapper {

    public static Recipe toEntity(RecipePersistRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        return recipe;
    }

    public static Recipe updateRecipe(Recipe recipe, RecipeUpdateRequestDto dto) {
        recipe.setDescription(dto.description());
        return recipe;
    }

    public static Recipe updateName(Recipe recipe, RecipeUpdateNameRequestDto dto) {
        recipe.setName(dto.name());
        return recipe;
    }

    public static RecipeResponseDto toDto(Recipe recipe) {
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getCalorie(),
                recipe.getProtein(),
                recipe.getFat(),
                recipe.getCarbohydrate());
    }
}
