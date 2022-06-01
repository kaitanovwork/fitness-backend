package kz.kaitanov.fitnessbackend.model.dto.request.user;

import kz.kaitanov.fitnessbackend.model.enums.Gender;

public record UserUpdateProfileRequestDto(
        String firstName,
        String lastName,
        Integer age,
        Gender gender) {
}
