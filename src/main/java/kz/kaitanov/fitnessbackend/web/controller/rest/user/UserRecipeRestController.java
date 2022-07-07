package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserRecipeRestController", description = "Контроллер для получения пользователем рецептов")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/recipe")
public class UserRecipeRestController {

    private final RecipeService recipeService;

    @Operation(summary = "Получение списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех рецептов успешно получен")
    })
    @GetMapping
    public Response<Page<Recipe>> getRecipePage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(recipeService.findAll(pageable));
    }
}
