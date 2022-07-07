package kz.kaitanov.fitnessbackend.model.dto.request.subMenu;

import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import kz.kaitanov.fitnessbackend.model.enums.WeekDay;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record SubMenuUpdateRequestDto(
        @NotNull @Positive Long id,
        @NotNull ProgramType programType,
        @NotNull WeekDay weekDay) {
}
