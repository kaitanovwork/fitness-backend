package kz.kaitanov.fitnessbackend.model.dto.request;

import kz.kaitanov.fitnessbackend.model.enums.Gender;

import javax.validation.constraints.NotBlank;

public record UserPersistRequestDto(
        @NotBlank String username,
        @NotBlank String password,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer age,
        Gender gender) {
}
