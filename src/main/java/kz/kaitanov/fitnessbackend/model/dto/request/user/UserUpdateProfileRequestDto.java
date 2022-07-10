package kz.kaitanov.fitnessbackend.model.dto.request.user;

import kz.kaitanov.fitnessbackend.model.enums.Gender;
import kz.kaitanov.fitnessbackend.model.enums.ProgramType;

public record UserUpdateProfileRequestDto(
        String firstName,
        String lastName,
        Integer age,
        Gender gender,
        Integer height,
        Integer weight,
        ProgramType programType) {
}
