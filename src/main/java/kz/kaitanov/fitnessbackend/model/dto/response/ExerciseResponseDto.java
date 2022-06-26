package kz.kaitanov.fitnessbackend.model.dto.response;

import kz.kaitanov.fitnessbackend.model.enums.Area;

public record ExerciseResponseDto(
        Long id,
        String name,
        String muscleGroup,
        Integer repeatCount,
        Integer approachCount,
        Area area) {
}
