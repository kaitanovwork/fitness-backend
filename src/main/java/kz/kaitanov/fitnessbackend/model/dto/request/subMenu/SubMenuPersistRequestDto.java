package kz.kaitanov.fitnessbackend.model.dto.request.subMenu;

import kz.kaitanov.fitnessbackend.model.enums.ProgramType;
import kz.kaitanov.fitnessbackend.model.enums.WeekDay;

import javax.validation.constraints.NotNull;

public record SubMenuPersistRequestDto(
        @NotNull ProgramType programType,
        @NotNull WeekDay weekDay) {
}

