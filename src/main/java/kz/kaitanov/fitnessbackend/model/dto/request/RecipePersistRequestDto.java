package kz.kaitanov.fitnessbackend.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public record RecipePersistRequestDto(
        @NotBlank String name,
        @PositiveOrZero Integer calorie,
        @PositiveOrZero Integer protein,
        @PositiveOrZero Integer fat,
        @PositiveOrZero Integer carbohydrate) {
}
