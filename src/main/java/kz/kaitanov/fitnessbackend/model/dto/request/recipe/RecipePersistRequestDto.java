package kz.kaitanov.fitnessbackend.model.dto.request.recipe;

import javax.validation.constraints.NotBlank;

public record RecipePersistRequestDto(
        @NotBlank String name,
        String description) {
}
