package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.converter.ExerciseMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExercisePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.exercise.ExerciseUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ExerciseService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
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
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminExerciseRestController", description = "CRUD операции над упражнениями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/exercise")
public class AdminExerciseRestController {
    //TODO подправить описание swagger AdminExerciseRestController
    private final ExerciseService exerciseService;

    @Operation(summary = "Создание нового упражнения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое упражнение успешно создано")
    })
    @PostMapping
    public Response<Exercise> saveExercise(@RequestBody @Valid ExercisePersistRequestDto dto) {
        ApiValidationUtil.requireFalse(exerciseService.existsByName(dto.name()), "name is being used by another exercise");
        return Response.ok(exerciseService.save(ExerciseMapper.toEntity(dto)));
    }

    @Operation(summary = "Обновление существующего упражнения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Существующее упражнение успешно обновлено"),
            @ApiResponse(responseCode = "404", description = "Упражнение не найдено")
    })
    @PutMapping
    public Response<Exercise> updateExercise(@RequestBody @Valid ExerciseUpdateRequestDto dto) {
        Optional<Exercise> exercise = exerciseService.findById(dto.id());
        ApiValidationUtil.requireTrue(exercise.isPresent(), String.format("Exercise by id %d not found", dto.id()));
        return Response.ok(exerciseService.update(ExerciseMapper.updateExercise(exercise.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для обновление наименования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Наименование продукта успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/name")
    public Response<Exercise> updateProductName(@RequestBody @Valid ExerciseUpdateNameRequestDto dto) {
        ApiValidationUtil.requireFalse(exerciseService.existsByName(dto.name()), "name is being used by another exercise");
        Optional<Exercise> exercise = exerciseService.findById(dto.id());
        ApiValidationUtil.requireTrue(exercise.isPresent(), String.format("Exercise by id %d not found", dto.id()));
        return Response.ok(exerciseService.update(ExerciseMapper.updateName(exercise.get(), dto)));
    }

    @Operation(summary = "Получение списка всех упражнений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех упражнений успешно получен")
    })
    @GetMapping
    public Response<List<Exercise>> getExerciseList() {
        return Response.ok(exerciseService.findAll());
    }

    @Operation(summary = "Получение упражнения по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Упражнение успешно получено"),
            @ApiResponse(responseCode = "404", description = "Упражнение не найдено")
    })
    @GetMapping(value = "/{exerciseId}")
    public Response<Exercise> getExerciseById(@PathVariable @Positive Long exerciseId) {
        Optional<Exercise> exercise = exerciseService.findById(exerciseId);
        ApiValidationUtil.requireTrue(exercise.isPresent(), String.format("Exercise by id %d not found", exerciseId));
        return Response.ok(exercise.get());
    }

    @Operation(summary = "Удаление упражнения по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Упражнение успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Упражнение не найдено")
    })
    @DeleteMapping(value = "/{exerciseId}")
    public Response<Void> deleteExerciseById(@PathVariable @Positive Long exerciseId) {
        ApiValidationUtil.requireTrue(exerciseService.existsById(exerciseId), String.format("Exercise by id %d not found", exerciseId));
        exerciseService.deleteById(exerciseId);
        return Response.ok();
    }
}
