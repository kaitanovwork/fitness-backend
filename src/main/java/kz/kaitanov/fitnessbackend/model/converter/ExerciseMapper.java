package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.dto.request.ExercisePersistRequestDto;

public final class ExerciseMapper {

    public static Exercise toEntity(ExercisePersistRequestDto dto) {
        Exercise exercise = new Exercise();
        exercise.setName(dto.name());
        exercise.setMuscleGroup(dto.muscleGroup());
        exercise.setRepeatCount(dto.repeatCount());
        exercise.setApproachCount(dto.approachCount());
        exercise.setArea(dto.area());
        return exercise;
    }
}
