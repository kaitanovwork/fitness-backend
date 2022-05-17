package kz.kaitanov.fitnessbackend.model.dto.request;

import kz.kaitanov.fitnessbackend.model.enums.Area;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record ExercisePersistRequestDto(
        @NotBlank String name,
        @NotBlank String muscleGroup,
        @Positive Integer repeatCount,
        @Positive Integer approachCount,
        Area area) {
}
