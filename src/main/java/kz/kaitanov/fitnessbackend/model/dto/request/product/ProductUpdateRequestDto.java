package kz.kaitanov.fitnessbackend.model.dto.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public record ProductUpdateRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String name,
        @NotNull @PositiveOrZero Integer calorie,
        @NotNull @PositiveOrZero Integer protein,
        @NotNull @PositiveOrZero Integer fat,
        @NotNull @PositiveOrZero Integer carbohydrate) {
}
