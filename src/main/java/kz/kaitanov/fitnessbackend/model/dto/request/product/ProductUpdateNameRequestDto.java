package kz.kaitanov.fitnessbackend.model.dto.request.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record ProductUpdateNameRequestDto(
        @NotNull @Positive Long id,
        @NotBlank String name) {
}
