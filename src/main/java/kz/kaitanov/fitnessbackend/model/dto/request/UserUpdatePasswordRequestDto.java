package kz.kaitanov.fitnessbackend.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record UserUpdatePasswordRequestDto(
        @Positive Long id,
        @NotBlank String password) {
}
