package kz.kaitanov.fitnessbackend.model.dto.request;

import javax.validation.constraints.NotBlank;

public record JwtRequest(
        @NotBlank String username,
        @NotBlank String password) {
}
