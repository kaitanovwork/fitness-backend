package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.Menu;
import kz.kaitanov.fitnessbackend.model.SubMenu;
import kz.kaitanov.fitnessbackend.model.converter.MenuMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuPersistRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.menu.MenuUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.service.implementations.model.MenuServiceImp;
import kz.kaitanov.fitnessbackend.service.interfaces.model.MenuService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.SubMenuService;
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

@Tag(name = "AdminMenuRestController", description = "CRUD операции над меню")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/admin/menu")
public class AdminMenuRestController {


    private final MenuService menuService;
    private final SubMenuService subMenuService;


    @Operation(summary = "Эндпоинт для создание нового меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новое меню успешно создано"),
            @ApiResponse(responseCode = "400", description = "Меню с таким id уже существует")
    })
    @PostMapping
    public Response<Menu> saveMenu(@RequestBody @Valid MenuPersistRequestDto dto) {
        return Response.ok(menuService.save(MenuMapper.toEntity(dto)));
    }

    @Operation(summary = "Эндпоинт для обновление существующего меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Меню успешно обновлено"),
            @ApiResponse(responseCode = "400", description = "Меню не найдено")
    })
    @PutMapping
    public Response<Menu> updateMenu(@RequestBody @Valid MenuUpdateRequestDto dto) {
        Optional<Menu> menu = menuService.findById(dto.id());
        ApiValidationUtil.requireTrue(menu.isPresent(), String.format("Menu by id %d not found", dto.id()));
        return Response.ok(menuService.update(MenuMapper.updateMenu(menu.get(), dto)));
    }

    @Operation(summary = "Эндпоинт для добавления подменю в меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подменю успешно добавлено"),
            @ApiResponse(responseCode = "400", description = "Меню или подменю не найдено")
    })
    @PutMapping("/{menuId}/subMenu/{subMenuId}")
    public Response<Menu> addSubMenuToMenu(@PathVariable @Positive Long menuId, @PathVariable @Positive Long subMenuId) {
        Optional<Menu> menu = menuService.findById(menuId);
        ApiValidationUtil.requireTrue(menu.isPresent(), String.format("Menu by id %d not found", menuId));
        Optional<SubMenu> subMenu = subMenuService.findById(subMenuId);
        ApiValidationUtil.requireTrue(subMenu.isPresent(), String.format("SubMenu by id %d not found", subMenuId));
        return Response.ok(menuService.addSubMenuToMenu(menu.get(), subMenu.get()));
    }

    @Operation(summary = "Эндпоинт для удаления подменю из меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Подменю успешно удалено"),
            @ApiResponse(responseCode = "400", description = "Меню или подменю не найдено")
    })
    @DeleteMapping("/{menuId}/subMenu/{subMenuId}")
    public Response<Menu> deleteSubMenuFromMenu(@PathVariable @Positive Long menuId, @PathVariable @Positive Long subMenuId) {
        Optional<Menu> menu = menuService.findByIdWithSubMenus(menuId);
        ApiValidationUtil.requireTrue(menu.isPresent(), String.format("Menu by id %d not found", menuId));
        Optional<SubMenu> subMenu = subMenuService.findById(subMenuId);
        ApiValidationUtil.requireTrue(subMenu.isPresent(), String.format("SubMenu by id %d not found", subMenuId));
        return Response.ok(menuService.deleteSubMenuFromMenu(menu.get(), subMenu.get()));
    }

    @Operation(summary = "Эндпоинт для получения списка всех меню")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех меню успешно получен")
    })
    @GetMapping
    public Response<List<Menu>> getMenuList() {
        return Response.ok(menuService.findAll());
    }

    @Operation(summary = "Эндпоинт для получения меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Меню успешно получено"),
            @ApiResponse(responseCode = "400", description = "Меню не найдено")
    })
    @GetMapping("/{menuId}")
    public Response<Menu> getMenuById(@PathVariable @Positive Long menuId) {
        Optional<Menu> menu = menuService.findById(menuId);
        ApiValidationUtil.requireTrue(menu.isPresent(), String.format("Menu by id %d not found", menuId));
        return Response.ok(menu.get());
    }

    @Operation(summary = "Эндпоинт для удаления меню по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Меню успешно удалено"),
            @ApiResponse(responseCode = "400", description = "Меню не найдено")
    })
    @DeleteMapping("/{menuId}")
    public Response<Void> deleteMenuById(@PathVariable @Positive Long menuId) {
        ApiValidationUtil.requireTrue(menuService.existsById(menuId), String.format("Menu by id %d not found", menuId));
        menuService.deleteById(menuId);
        return Response.ok();
    }
}
