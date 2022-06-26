package kz.kaitanov.fitnessbackend.service.implementations.model;

import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.converter.ExerciseMapper;
import kz.kaitanov.fitnessbackend.model.dto.response.ExerciseResponseDto;
import kz.kaitanov.fitnessbackend.repository.model.ExerciseRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ExerciseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExerciseServiceImpl extends AbstractServiceImpl<Exercise, Long> implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        super(exerciseRepository);
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public boolean existsByName(String name) {
        return exerciseRepository.existsByName(name);
    }

    @Override
    public Page<ExerciseResponseDto> findAll(Pageable pageable) {
        return exerciseRepository.findAll(pageable).map(ExerciseMapper::toDto);
    }
}
