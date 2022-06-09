package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Exercise;

public interface ExerciseService extends AbstractService<Exercise, Long> {

    boolean existsByName(String name);
}
