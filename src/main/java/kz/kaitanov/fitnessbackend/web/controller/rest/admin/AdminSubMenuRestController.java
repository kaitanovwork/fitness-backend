package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.model.converter.SubMenuMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.subMenu.SubMenuUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;

import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Tag(name = "AdminSubMenuRestController", description = "CRUD операции над суб-меню")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/sub-menu")
public class AdminSubMenuRestController {

    private final SubMenuService subMenuService;
    private final RecipeService recipeService;

    @Operation(summary = "Эндпоинт для создания нового суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое суб-меню успешно создано")
    })
    @PostMapping
    public Response<SubMenu> saveSubMenu(@RequestBody @Valid SubMenuPersistRequestDto dto) {
        return Response.ok(subMenuService.save(SubMenuMapper.toEntity(dto)));
    }

    @Operation(summary = "Эндпоинт для обновления существующего суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Суб-меню успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Суб-меню не найдено")
    })
    @PutMapping
    public Response<SubMenu> updateSubMenu(@RequestBody @Valid SubMenuUpdateRequestDto dto) {
        Optional<SubMenu> subMenu = subMenuService.findById(dto.id());
        ApiValidationUtil.requireTrue(subMenu.isPresent(), String.format("SubMenu with id %d not found", dto.id()));
        return Response.ok(subMenuService.update(SubMenuMapper.updateSubMenu(subMenu.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для добавление рецепта к суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно добавлен к суб-меню"),
            @ApiResponse(responseCode = "400", description = "Указан несуществующий id для суб-меню или рецепта")
    })
    @PutMapping("/{subMenuId}/recipe/{recipeId}")
    public Response<SubMenu> addRecipeToSubMenu(@PathVariable @Positive Long subMenuId, @PathVariable @Positive Long recipeId) {
        Optional<SubMenu> subMenuOptional = subMenuService.findByIdWithRecipes(subMenuId);
        ApiValidationUtil.requireTrue(subMenuOptional.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
        ApiValidationUtil.requireTrue(recipeOptional.isPresent(), String.format("Recipe with id %d not found", recipeId));
        return Response.ok(subMenuService.addRecipeToSubMenu(subMenuOptional.get(), recipeOptional.get()));
    }

    @Operation(summary = "Эндпоинт для удаление рецепта из суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно удален из суб-меню"),
            @ApiResponse(responseCode = "400", description = "Рецепт или суб-меню не найдены")
    })
    @DeleteMapping("{subMenuId}/recipe/{recipeId}")
    public Response<SubMenu> deleteRecipeFromSubMenu(@PathVariable @Positive Long subMenuId, @PathVariable @Positive Long recipeId) {
        Optional<SubMenu> subMenuOptional = subMenuService.findByIdWithRecipes(subMenuId);
        ApiValidationUtil.requireTrue(subMenuOptional.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        Optional<Recipe> recipeOptional = recipeService.findById(recipeId);
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
    public Response<Page<SubMenu>> getSubMenuPage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(subMenuService.findAll(pageable));
    }

    @Operation(summary = "Эндпоинт для удаления суб-меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Суб-меню успешно удалено"),
            @ApiResponse(responseCode = "400", description = "Суб-меню с указанным id не существует")
    })
    @DeleteMapping("/{subMenuId}")
    public Response<Void> deleteSubMenuById(@PathVariable @Positive Long subMenuId) {
        ApiValidationUtil.requireTrue(subMenuService.existsById(subMenuId), String.format("SubMenu with id %d not found", subMenuId));
        subMenuService.deleteById(subMenuId);
        return Response.ok();
    }

    @Operation(summary = "Эндпоинт для добавления рецептов пачкой к суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепты успешно добавлены к суб-меню"),
            @ApiResponse(responseCode = "400", description = "Указаны несуществующие id для суб-меню или рецепта")
    })
    @PutMapping("/{subMenuId}/recipe")
    public Response<SubMenu> addRecipesToSubMenu(@PathVariable @Positive Long subMenuId, @RequestParam @NotNull Long[] recipesId) {
        Optional<SubMenu> subMenu = subMenuService.findByIdWithRecipes(subMenuId);
        ApiValidationUtil.requireTrue(subMenu.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        return Response.ok(subMenuService.addRecipesToSubMenu(subMenu.get(), recipeService.findByIds(recipesId)));
    }

    @Operation(summary = "Эндпоинт для удаление рецепта из суб-меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепты успешно удалены из суб-меню"),
            @ApiResponse(responseCode = "400", description = "Рецепт или суб-меню не найдены")
    })
    @DeleteMapping("{subMenuId}/recipe")
    public Response<SubMenu> deleteRecipesFromSubMenu(@PathVariable @Positive Long subMenuId, @RequestParam @NotNull Long[] recipesId) {
        Optional<SubMenu> subMenu = subMenuService.findByIdWithRecipes(subMenuId);
        ApiValidationUtil.requireTrue(subMenu.isPresent(), String.format("SubMenu with id %d not found", subMenuId));
        return Response.ok(subMenuService.deleteRecipesFromSubMenu(subMenu.get(), recipeService.findByIds(recipesId)));
    }
}
