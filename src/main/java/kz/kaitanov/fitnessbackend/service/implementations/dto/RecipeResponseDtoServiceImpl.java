package kz.kaitanov.fitnessbackend.service.implementations.dto;

import kz.kaitanov.fitnessbackend.model.converter.RecipeMapper;
import kz.kaitanov.fitnessbackend.model.dto.response.RecipeResponseDto;
import kz.kaitanov.fitnessbackend.repository.dto.RecipeResponseDtoRepository;
import kz.kaitanov.fitnessbackend.repository.model.RecipeRepository;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.RecipeResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecipeResponseDtoServiceImpl implements RecipeResponseDtoService {

    private final RecipeResponseDtoRepository recipeResponseDtoRepository;
    private final RecipeRepository recipeRepository;
    @Override
    public Page<RecipeResponseDto> findAll(Pageable pageable) {
        return recipeRepository.findAll(pageable).map(RecipeMapper::toDto);
    }
}
