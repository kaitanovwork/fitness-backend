package kz.kaitanov.fitnessbackend.model.converter;

import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExercisePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateRequestDto;

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

    public static Exercise updateExercise(Exercise exercise, ExerciseUpdateRequestDto dto) {
        exercise.setMuscleGroup(dto.muscleGroup());
        exercise.setRepeatCount(dto.repeatCount());
        exercise.setApproachCount(dto.approachCount());
        exercise.setArea(dto.area());
        return exercise;
    }

    public static Exercise updateName(Exercise exercise, ExerciseUpdateNameRequestDto dto) {
        exercise.setName(dto.name());
        return exercise;
    }
}
