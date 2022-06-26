package kz.kaitanov.fitnessbackend.model.dto.response;

public record RecipeResponseDto(
        Long id,
        String name,
        String description,
        Integer calorie,
        Integer protein,
        Integer fat,
        Integer carbohydrate) {
}
