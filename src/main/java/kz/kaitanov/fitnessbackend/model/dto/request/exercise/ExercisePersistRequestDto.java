package kz.kaitanov.fitnessbackend.model.dto.request.exercise;

import kz.kaitanov.fitnessbackend.model.enums.Area;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ExercisePersistRequestDto(
        @NotBlank String name,
        @NotBlank String muscleGroup,
        @NotNull @Positive Integer repeatCount,
        @NotNull @Positive Integer approachCount,
        @NotNull Area area) {
}
