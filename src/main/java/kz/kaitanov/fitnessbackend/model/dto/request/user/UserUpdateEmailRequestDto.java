package kz.kaitanov.fitnessbackend.model.dto.request.user;

import javax.validation.constraints.NotBlank;

public record UserUpdateEmailRequestDto(@NotBlank String email) {
}
