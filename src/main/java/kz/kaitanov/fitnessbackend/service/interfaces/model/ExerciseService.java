package kz.kaitanov.fitnessbackend.service.interfaces.model;

import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.dto.response.ExerciseResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExerciseService extends AbstractService<Exercise, Long> {

    boolean existsByName(String name);

    Page<ExerciseResponseDto> findAll(Pageable pageable);
}
