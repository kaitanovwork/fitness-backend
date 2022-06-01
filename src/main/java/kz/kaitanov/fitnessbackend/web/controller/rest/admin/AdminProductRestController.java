package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.converter.ProductMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.Response;
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
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminProductRestController", description = "CRUD операции над продуктами")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductRestController {

    private final ProductService productService;

    @Operation(summary = "Создание нового продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое упражнение успешно создано")
    })
    @PostMapping
    public Response<Product> saveProduct(@RequestBody @Valid ProductPersistRequestDto dto) {
        return Response.ok(productService.save(ProductMapper.toEntity(dto)));
    }

    @Operation(summary = "Обновление существующего продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущестующий продкут успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @PutMapping("/{productId}")
    public Response<Product> updateProduct(@PathVariable("productId") Long productId, @RequestBody @Valid Product product) {
        ApiValidationUtil.requireTrue(productService.existsById(productId), String.format("Product by id %d not found", productId));
        return Response.ok(productService.update(product));
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
    public Response<Product> getProductById(@PathVariable Long productId) {
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
        productService.deleteById(productId);
        return Response.ok();
    }
}
