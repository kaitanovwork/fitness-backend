package kz.kaitanov.fitnessbackend.repository.dto;

import kz.kaitanov.fitnessbackend.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeResponseDtoRepository extends JpaRepository<Recipe, Long> {
}
