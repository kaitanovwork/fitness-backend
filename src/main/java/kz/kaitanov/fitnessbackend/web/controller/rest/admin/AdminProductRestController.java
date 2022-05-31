package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.converter.ProductMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.ProductUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.ProductResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.ProductResponseDtoService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminProductRestController", description = "CRUD операции над продуктами со стороны админа")
@RestController
@Validated
@RequestMapping("/api/admin/v1/product")
public class AdminProductRestController {

    ProductService productService;
    ProductResponseDtoService productResponseDtoService;

    public AdminProductRestController (
            ProductService productService,
            ProductResponseDtoService productResponseDtoService) {
        this.productResponseDtoService = productResponseDtoService;
        this.productService = productService;
    }


    @Operation(summary = "Создание нового продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое упражнение успешно создано")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> saveProduct(@RequestBody @Valid ProductPersistRequestDto productPersistRequestDto) {
        Product product = productService.save(ProductMapper.toEntity(productPersistRequestDto));
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Operation(summary = "Обновление существующего продукта")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сущестующий продкут успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @PutMapping
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody @Valid ProductUpdateRequestDto productUpdateRequestDto) {
        if(!productService.existsById(productUpdateRequestDto.id())) {
            return ResponseEntity.notFound().build();
        }
        Product product = productService.update(ProductMapper.toEntity(productUpdateRequestDto));
        return ResponseEntity.ok(ProductMapper.toDto(product));
    }

    @Operation(summary = "Получене списка всех продуктов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех продуктов успешно получен"),

    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProductList() {
        return ResponseEntity.ok(productResponseDtoService.findAll());
    }

    @Operation(summary = "Получение продукта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно получен"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long productId) {
        Optional<ProductResponseDto> productResponseDtoOptional = productResponseDtoService.findById(productId);
        return productResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение продукта по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно найден"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @GetMapping("/productName")
    public ResponseEntity<ProductResponseDto> getProductByName(@PathVariable String productName) {
        Optional<ProductResponseDto> productResponseDtoOptional = productResponseDtoService.findByName(productName);
        return productResponseDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удаление продукта по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Продукт успешно удален"),
            @ApiResponse(responseCode = "404", description = "Продукт не найден")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        if (!productService.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteById(productId);
        return ResponseEntity.ok().build();
    }





}
