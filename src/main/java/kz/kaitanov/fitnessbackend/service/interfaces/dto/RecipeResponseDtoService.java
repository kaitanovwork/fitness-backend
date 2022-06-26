package kz.kaitanov.fitnessbackend.service.interfaces.dto;

import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.dto.response.RecipeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeResponseDtoService {

    Page<RecipeResponseDto> findAll(Pageable pageable);
}
