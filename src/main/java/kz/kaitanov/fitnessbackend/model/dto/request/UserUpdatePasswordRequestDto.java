package kz.kaitanov.fitnessbackend.model.dto.request;

import javax.validation.constraints.Positive;

public record UserUpdatePasswordRequestDto(
        @Positive Long id,
        String password) {
}
