package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ExerciseService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Tag(name = "UserExerciseRestController", description = "Контроллер для получения списка всех упражнений у пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/exercise")
public class UserExerciseRestController {

    private final ExerciseService exerciseService;

    @Operation(summary = "Эндпоинт для получения списка всех упражнений у пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех упражнений с пагинацией успешно получен"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @GetMapping
    public Response<Page<Exercise>> getExercisePage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(exerciseService.findAll(pageable));
    }

}
