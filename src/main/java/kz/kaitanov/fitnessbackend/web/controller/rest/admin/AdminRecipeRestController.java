package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.Recipe;
import kz.kaitanov.fitnessbackend.model.converter.RecipeMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipePersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.recipe.RecipeUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RecipeService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Tag(name = "AdminRecipeRestController", description = "CRUD операции над рецептами")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/recipe")
public class AdminRecipeRestController {

    private final RecipeService recipeService;
    private final ProductService productService;

    @Operation(summary = "Эндпоинт для создание нового рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый рецепт успешно создан"),
            @ApiResponse(responseCode = "400", description = "Наименование используется в другом рецепте")
    })
    @PostMapping
    public Response<Recipe> saveRecipe(@RequestBody @Valid RecipePersistRequestDto dto) {
        ApiValidationUtil.requireFalse(recipeService.existsByName(dto.name()), "name is being used by another recipe");
        return Response.ok(recipeService.save(RecipeMapper.toEntity(dto)));
    }

    @Operation(summary = "Эндпоинт для обновление существующего рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Рецпет не найден")
    })
    @PutMapping
    public Response<Recipe> updateRecipe(@RequestBody @Valid RecipeUpdateRequestDto dto) {
        Optional<Recipe> recipe = recipeService.findById(dto.id());
        ApiValidationUtil.requireTrue(recipe.isPresent(), String.format("Recipe by id %d not found", dto.id()));
        return Response.ok(recipeService.update(RecipeMapper.updateRecipe(recipe.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для обновление наименования рецепта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Наименование рецепта успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Наименование используется в другом рецепте или рецепт не найден")
    })
    @PutMapping("/name")
    public Response<Recipe> updateRecipeName(@RequestBody @Valid RecipeUpdateNameRequestDto dto) {
        ApiValidationUtil.requireFalse(recipeService.existsByName(dto.name()), "name is being used by another recipe");
        Optional<Recipe> recipe = recipeService.findById(dto.id());
        ApiValidationUtil.requireTrue(recipe.isPresent(), String.format("Recipe by id %d not found", dto.id()));
        return Response.ok(recipeService.update(RecipeMapper.updateName(recipe.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для добавления продукта в рецепт")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Рецепт или продукт не найден")
    })
    @PutMapping("/{recipeId}/product/{productId}")
    public Response<Recipe> addProductToRecipe(@PathVariable @Positive Long recipeId, @PathVariable @Positive Long productId) {
        Optional<Recipe> recipe = recipeService.findByIdWithProducts(recipeId);
        ApiValidationUtil.requireTrue(recipe.isPresent(), String.format("Recipe by id %d not found", recipeId));
        Optional<Product> product = productService.findById(productId);
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by id %d not found", productId));
        return Response.ok(recipeService.addProductToRecipe(recipe.get(), product.get()));
    }

    @Operation(summary = "Эндпоинт для удаления продукта из рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно удален"),
            @ApiResponse(responseCode = "400", description = "Рецепт или продукт не найден")
    })
    @DeleteMapping("/{recipeId}/product/{productId}")
    public Response<Recipe> deleteProductFromRecipe(@PathVariable @Positive Long recipeId, @PathVariable @Positive Long productId) {
        Optional<Recipe> recipe = recipeService.findByIdWithProducts(recipeId);
        ApiValidationUtil.requireTrue(recipe.isPresent(), String.format("Recipe by id %d not found", recipeId));
        Optional<Product> product = productService.findById(productId);
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by id %d not found", productId));
        return Response.ok(recipeService.deleteProductFromRecipe(recipe.get(), product.get()));
    }

    @Operation(summary = "Эндпоинт для получения списка всех рецептов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех рецептов успешно получен")
    })
    @GetMapping
    public Response<Page<Recipe>> getRecipePage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(recipeService.findAll(pageable));
    }

    @Operation(summary = "Эндпоинт для получения рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно получен"),
            @ApiResponse(responseCode = "400", description = "Рецепт не найден")
    })
    @GetMapping("/{recipeId}")
    public Response<Recipe> getRecipeById(@PathVariable @Positive Long recipeId) {
        Optional<Recipe> recipe = recipeService.findById(recipeId);
        ApiValidationUtil.requireTrue(recipe.isPresent(), String.format("Recipe by id %d not found", recipeId));
        return Response.ok(recipe.get());
    }

    @Operation(summary = "Эндпоинт для удаления рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рецепт успешно удален"),
            @ApiResponse(responseCode = "400", description = "Рецепт не найден")
    })
    @DeleteMapping("/{recipeId}")
    public Response<Void> deleteRecipeById(@PathVariable @Positive Long recipeId) {
        ApiValidationUtil.requireTrue(recipeService.existsById(recipeId), String.format("Recipe by id %d not found", recipeId));
        recipeService.deleteById(recipeId);
        return Response.ok();
    }

    //TODO добавить эндпоинт для добавления продуктов пачкой
    //TODO добавить эндпоинт для удаления продуктов пачкой
}
