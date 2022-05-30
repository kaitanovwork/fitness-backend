package kz.kaitanov.fitnessbackend.model.dto.request;

import kz.kaitanov.fitnessbackend.model.enums.Gender;
import javax.validation.constraints.Positive;

public record UserUpdateProfileRequestDto(
        @Positive Long id,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer age,
        Gender gender) {
}
