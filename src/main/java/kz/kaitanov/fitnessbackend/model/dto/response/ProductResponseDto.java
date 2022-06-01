package kz.kaitanov.fitnessbackend.model.dto.response;


public record ProductResponseDto(
         Long id,
         String name,
         Integer calorie,
         Integer protein,
         Integer fat,
         Integer carbohydrate) {
}
