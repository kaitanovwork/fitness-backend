package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Product;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.model.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "UserProductRestController", description = "Контроллер для получения пользователем продуктов")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user/product")
public class UserProductRestController {

    private final ProductService productService;

    @Operation(summary = "Получение списка всех продуктов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех продуктов успешно получен")
    })
    @GetMapping
    public Response<Page<Product>> getProductPage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(productService.findAll(pageable));
    }
}
