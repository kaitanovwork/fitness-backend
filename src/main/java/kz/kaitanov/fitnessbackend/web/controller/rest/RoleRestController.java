package kz.kaitanov.fitnessbackend.web.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Role;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Optional;

@Tag(name = "RoleRestController", description = "CRUD операции над ролями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/role")
public class RoleRestController {

    private final RoleService roleService;

    @Operation(summary = "Получение списка всех ролей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех ролей успешно получен")
    })
    @GetMapping
    public ResponseEntity<List<Role>> getRoleList() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @Operation(summary = "Получение роли по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно получена"),
            @ApiResponse(responseCode = "404", description = "Роль не найдена")
    })
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRoleById(@PathVariable @Positive Long roleId) {
        Optional<Role> roleOptional = roleService.findById(roleId);
        return roleOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получение роли по имени")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Роль успешно получена"),
            @ApiResponse(responseCode = "404", description = "Роль не найдена")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable RoleName name) {
        Optional<Role> roleOptional = roleService.findByName(name);
        return roleOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
