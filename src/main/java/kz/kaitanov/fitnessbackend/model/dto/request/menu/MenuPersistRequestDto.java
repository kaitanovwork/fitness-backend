package kz.kaitanov.fitnessbackend.model.dto.request.menu;

import kz.kaitanov.fitnessbackend.model.enums.ProgramType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record MenuPersistRequestDto(
        @NotNull ProgramType programType
        ) {
}
