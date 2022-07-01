package kz.kaitanov.fitnessbackend.model.dto.request.exercise;

import kz.kaitanov.fitnessbackend.model.enums.Area;
import kz.kaitanov.fitnessbackend.model.enums.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ExerciseUpdateRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String muscleGroup,
        @NotNull @Positive Integer repeatCount,
        @NotNull @Positive Integer approachCount,
        @NotNull Area area,
        @NotNull Category category) {
}
