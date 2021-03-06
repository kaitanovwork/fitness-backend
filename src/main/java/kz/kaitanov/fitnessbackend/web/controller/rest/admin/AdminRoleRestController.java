package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminRoleRestController", description = "CRUD операции над ролями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/role")
public class AdminRoleRestController {

    private final RoleService roleService;

    @Operation(summary = "Получение списка всех ролей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех ролей успешно получен")
    })
    @GetMapping
    public Response<List<Role>> getRoleList() {
        return Response.ok(roleService.findAll());
    }

    @Operation(summary = "Получение роли по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно получена"),
            @ApiResponse(responseCode = "400", description = "Роль не найдена")
    })
    @GetMapping("/{roleId}")
    public Response<Role> getRoleById(@PathVariable @Positive Long roleId) {
        Optional<Role> role = roleService.findById(roleId);
        ApiValidationUtil.requireTrue(role.isPresent(), String.format("Role by id %d not found", roleId));
        return Response.ok(role.get());
    }

    @Operation(summary = "Получение роли по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно получена")
    })
    @GetMapping("/name/{name}")
    public Response<Role> getRoleByName(@PathVariable RoleName name) {
        return Response.ok(roleService.findByName(name));
    }
}
