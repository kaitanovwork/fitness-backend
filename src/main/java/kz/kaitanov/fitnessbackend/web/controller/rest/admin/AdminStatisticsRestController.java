package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AdminStatisticsRestController", description = "CRUD статистикой")
@Validated
@RestController
@RequiredArgsConstructor
public class AdminStatisticsRestController {
    private final UserResponseDtoService userService;

    @Operation(summary = "Эндпоинт для получения числа пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Число всех пользователей успешно получено")
    })
    @GetMapping("/api/v1/admin/statistics")
    public Response<Integer> getUserWithUserRoleCount() {
        System.out.println(userService.getUsersWithUserRoleCount());
        return Response.ok(userService.getUsersWithUserRoleCount());
    }


}
