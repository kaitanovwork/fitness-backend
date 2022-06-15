package kz.kaitanov.fitnessbackend.model.dto.request.user;

import kz.kaitanov.fitnessbackend.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record UserUpdateCoachRequestDto(

        @NotNull @Positive Long id,
        User coach) {

}
