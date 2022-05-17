package kz.kaitanov.fitnessbackend.web.controller.model;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.converter.RecipeMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.RecipePersistRequestDto;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Tag(name = "RecipeRestController", description = "CRUD операции над рецептами")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeRestController {

    private final RecipeService recipeService;

    @Operation(summary = "Создание нового рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый рецепт успешно создан")
    })
    @PostMapping
    public ResponseEntity<Recipe> saveRecipe(@RequestBody @Valid RecipePersistRequestDto recipePersistRequestDto) {
        return ResponseEntity.ok(recipeService.save(RecipeMapper.toEntity(recipePersistRequestDto)));
    }

    @Operation(summary = "Обновление существующего рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Существующий рецепт успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Рецпет не найден")
    })
    @PutMapping
    public ResponseEntity<Recipe> updateRecipe(@RequestBody @Valid Recipe recipe) {
        if (!recipeService.existsById(recipe.getId())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeService.update(recipe));
    }

    @Operation(summary = "Получение списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех рецептов успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<Recipe>> getRecipeList() {
        return ResponseEntity.ok(recipeService.findAll());
    }

    @Operation(summary = "Получение рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно получен"),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    @GetMapping("/{recipeId}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable @Positive Long recipeId) {
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        return recipeOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable @Positive Long recipeId) {
        if (!recipeService.existsById(recipeId)) {
            return ResponseEntity.notFound().build();
        }
        recipeService.deleteById(recipeId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Получение рецепта по названию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно получен"),
            @ApiResponse(responseCode = "404", description = "Рецепт не найден")
    })
    @GetMapping("/name/{recipeName}")
    public ResponseEntity<List<Recipe>> getRecipeListByName(@PathVariable @NotBlank String recipeName) {
        return ResponseEntity.ok(recipeService.findAllByName(recipeName));
    }
}
