package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.converter.ProductMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateNameRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.product.ProductUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
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

@Tag(name = "AdminProductRestController", description = "CRUD операции над продуктами")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductRestController {
    //TODO подправить описание swagger AdminProductRestController
    private final ProductService productService;

    @Operation(summary = "Создание нового продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое упражнение успешно создано")
    })
    @PostMapping
    public Response<Product> saveProduct(@RequestBody @Valid ProductPersistRequestDto dto) {
        ApiValidationUtil.requireFalse(productService.existsByName(dto.name()), "name is being used by another product");
        return Response.ok(productService.save(ProductMapper.toEntity(dto)));
    }

    @Operation(summary = "Обновление существующего продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущестующий продкут успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @PutMapping
    public Response<Product> updateProduct(@RequestBody @Valid ProductUpdateRequestDto dto) {
        Optional<Product> product = productService.findById(dto.id());
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by id %d not found", dto.id()));
        return Response.ok(productService.update(ProductMapper.updateProduct(product.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для обновление наименования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Наименование продукта успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/name")
    public Response<Product> updateProductName(@RequestBody @Valid ProductUpdateNameRequestDto dto) {
        ApiValidationUtil.requireFalse(productService.existsByName(dto.name()), "name is being used by another product");
        Optional<Product> product = productService.findById(dto.id());
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by id %d not found", dto.id()));
        return Response.ok(productService.update(ProductMapper.updateName(product.get(), dto)));
    }

    @Operation(summary = "Получене списка всех продуктов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех продуктов успешно получен"),
    })
    @GetMapping
    public Response<List<Product>> getProductList() {
        return Response.ok(productService.findAll());
    }

    @Operation(summary = "Получение продукта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно получен"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @GetMapping("/{productId}")
    public Response<Product> getProductById(@PathVariable @Positive Long productId) {
        Optional<Product> product = productService.findById(productId);
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by id %d not found", productId));
        return Response.ok(product.get());
    }

    @Operation(summary = "Получение продукта по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно найден"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @GetMapping("/productName/{productName}")
    public Response<Product> getProductByName(@PathVariable String productName) {
        Optional<Product> product = productService.findByName(productName);
        ApiValidationUtil.requireTrue(product.isPresent(), String.format("Product by name %s not found", productName));
        return Response.ok(product.get());
    }

    @Operation(summary = "Удаление продукта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @DeleteMapping("/{productId}")
    public Response<Void> deleteProductById(@PathVariable Long productId) {
        ApiValidationUtil.requireTrue(productService.existsById(productId), String.format("Product by id %d not found", productId));
        productService.deleteById(productId);
        return Response.ok();
    }
}
