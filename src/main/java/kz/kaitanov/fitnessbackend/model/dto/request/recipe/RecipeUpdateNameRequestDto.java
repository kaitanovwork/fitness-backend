package kz.kaitanov.fitnessbackend.model.dto.request.recipe;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record RecipeUpdateNameRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String name) {
}
