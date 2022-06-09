package kz.kaitanov.fitnessbackend.model.dto.request.exercise;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ExerciseUpdateNameRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String name) {
}
