package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminSubMenuRestController", description = "CRUD операции над суб-меню")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/sub-menu")
public class AdminSubMenuRestController {

    private final SubMenuService subMenuService;
    private final RecipeService recipeService;

    @Operation(summary = "Эндпоинт для добавление рецепта к суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно добавлен к суб-меню"),
            @ApiResponse(responseCode = "400", description = "Указан несуществующий id для суб-меню или рецепта")
    })
    @PutMapping("/{subMenuId}/recipe/{recipeId}")
    public Response<SubMenu> addRecipeToSubMenu (@PathVariable @Positive Long subMenuId, @PathVariable @Positive  Long recipeId) {
        Optional<SubMenu> subMenuOptional = subMenuService.findById(subMenuId);
        ApiValidationUtil.requireTrue(subMenuOptional.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        Optional <Recipe> recipeOptional = recipeService.findById(recipeId);
        ApiValidationUtil.requireTrue(recipeOptional.isPresent(), String.format("Recipe with id %d not found", recipeId));
        return Response.ok(subMenuService.addRecipeToSubMenu(subMenuOptional.get(), recipeOptional.get()));
    }

    @Operation(summary = "Эндпоинт для удаление рецепта из суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно удален из суб-меню"),
            @ApiResponse(responseCode = "400", description =  "Продукт или ")
    })
    @DeleteMapping("{subMenuId}/recipe/{recipeId}")
    public Response<SubMenu> deleteRecipeFromSubMenu (@PathVariable @Positive Long subMenuId, @PathVariable @Positive Long recipeId) {
        Optional<SubMenu> subMenuOptional = subMenuService.findByIdWithRecipes(subMenuId);
        ApiValidationUtil.requireTrue(subMenuOptional.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        Optional <Recipe> recipeOptional = recipeService.findById(recipeId);
        ApiValidationUtil.requireTrue(recipeOptional.isPresent(), String.format("Recipe with id %d not found", recipeId));
        return Response.ok(subMenuService.deleteRecipeFromSubMenu(subMenuOptional.get(), recipeOptional.get()));
    }
    @Operation(summary = "Эндпоинт для получения суб-меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Суб-меню успешно получено"),
            @ApiResponse(responseCode = "400", description = "Суб-меню с указанным id не существует")
    })
    @GetMapping("/{subMenuId}")
    public Response<SubMenu> getSubMenuById(@PathVariable @Positive Long subMenuId) {
        Optional<SubMenu> subMenuOptional = subMenuService.findById(subMenuId);
        ApiValidationUtil.requireTrue(subMenuOptional.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        return Response.ok(subMenuOptional.get());
    }

    @Operation(summary = "Эндпоинт для получения списка суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список суб-меню успешно получен"),
    })
    @GetMapping
    public Response<List<SubMenu>> getSubMenuList() {
        return Response.ok(subMenuService.findAll());
    }

    @Operation(summary = "Эндпоинт для удаления суб-меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Суб-меню успешно удалено"),
            @ApiResponse(responseCode = "400", description = "Суб-меню с указанным id не существует")
    })
    @DeleteMapping("/{subMenuId}")
    public Response<Void> deleteSubMenuById(@PathVariable @Positive Long subMenuId) {
        ApiValidationUtil.requireTrue(subMenuService.existsById(subMenuId),String.format("SubMenu with id %d not found", subMenuId));
        subMenuService.deleteById(subMenuId);
        return Response.ok();
    }
}
