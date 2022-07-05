package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Exercise;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "UserExerciseRestController", description = "Контроллер для получения списка всех упражнений у пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/{userId}/exercise")
public class UserExerciseRestController {

    private final ExerciseService exerciseService;

    @Operation(summary = "Эндпоинт для получения списка всех упражнений у пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех упражнений с пагинацией успешно получен"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @GetMapping()
    @PreAuthorize(value = "hasAuthority('ADMIN')" + " or #userId == authentication.principal.id")
    public Response<Page<Exercise>> getExercisePage(@PathVariable("userId") Long userId, Principal principal, Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user.getId() == userId){
            Response.ok(exerciseService.findAll(pageable));
        }
        return Response.ok(exerciseService.findAll(pageable));
    }
}