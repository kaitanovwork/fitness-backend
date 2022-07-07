package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateRequestDto;

public final class RecipeMapper {

    public static Recipe toEntity(RecipePersistRequestDto dto) {
        Recipe recipe = new Recipe();
        recipe.setName(dto.name());
        recipe.setDescription(dto.description());
        recipe.setPicUrl(dto.picUrl());
        return recipe;
    }

    public static Recipe updateRecipe(Recipe recipe, RecipeUpdateRequestDto dto) {
        recipe.setDescription(dto.description());
        recipe.setPicUrl(dto.picUrl());
        return recipe;
    }

    public static Recipe updateName(Recipe recipe, RecipeUpdateNameRequestDto dto) {
        recipe.setName(dto.name());
        return recipe;
    }
}
