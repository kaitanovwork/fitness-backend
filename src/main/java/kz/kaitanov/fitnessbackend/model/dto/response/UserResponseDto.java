package kz.kaitanov.fitnessbackend.model.dto.response;

import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.enums.Gender;
import kz.kaitanov.fitnessbackend.model.enums.ProgramType;

public record UserResponseDto(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer age,
        Gender gender,
        User coach,
        Integer height,
        Integer weight,
        ProgramType programType) {
}
