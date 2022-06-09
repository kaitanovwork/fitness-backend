package kz.kaitanov.fitnessbackend.model.dto.request.user;

import kz.kaitanov.fitnessbackend.model.enums.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record UserUpdateRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String username,
        @NotBlank String password,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer age,
        Gender gender) {
}
