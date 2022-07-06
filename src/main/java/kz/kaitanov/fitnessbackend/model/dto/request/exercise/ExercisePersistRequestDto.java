package kz.kaitanov.fitnessbackend.model.dto.request.exercise;

import kz.kaitanov.fitnessbackend.model.enums.Area;
import kz.kaitanov.fitnessbackend.model.enums.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public record ExercisePersistRequestDto(
        @NotBlank String name,
        @NotNull @Positive Integer repeatCount,
        @NotNull @Positive Integer approachCount,
        @NotNull Area area,
        @NotNull Category category) {
}
