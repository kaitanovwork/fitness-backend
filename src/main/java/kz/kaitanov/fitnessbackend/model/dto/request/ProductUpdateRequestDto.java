package kz.kaitanov.fitnessbackend.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public record ProductUpdateRequestDto(
        @Positive Long id,
        @NotBlank String name,
        @PositiveOrZero Integer calorie,
        @PositiveOrZero Integer protein,
        @PositiveOrZero Integer fat,
        @PositiveOrZero Integer carbohydrate) {
        }