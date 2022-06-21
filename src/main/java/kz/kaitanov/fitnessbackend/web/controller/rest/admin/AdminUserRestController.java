package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.RoleService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Optional;

@Tag(name = "AdminUserRestController", description = "CRUD  операции над пользователями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserRestController {

    private final UserService userService;
    private final UserResponseDtoService userResponseDtoService;
    private final RoleService roleService;

    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан")
    })
    @PostMapping
    public Response<UserResponseDto> saveUser(@RequestBody @Valid UserRegistrationRequestDto userRegistrationRequestDto) {
        User user = userService.save(UserMapper.toEntity(userRegistrationRequestDto));
        return Response.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Обновление существующего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Существующий пользователь успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @PutMapping
    public Response<UserResponseDto> updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto) {
        ApiValidationUtil.requireTrue(userService.existsById(userUpdateRequestDto.id()),
                String.format("User by id %d not found", userUpdateRequestDto.id()));

        User user = userService.update(UserMapper.toEntity(userUpdateRequestDto));
        return Response.ok(UserMapper.toDto(user));
    }

    @Operation(summary = "Получение списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех пользователей успешно получен")
    })
    @GetMapping
    public Response<List<UserResponseDto>> getUserList() {
        return Response.ok(userResponseDtoService.findAll());
    }

    @Operation(summary = "Получение постраничного списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Постраничный список всех пользователей успешно получен")
    })
    @GetMapping("/users")
    public Response<Page<UserResponseDto>> getPaginatedUsers(
            @RequestParam(required = false, defaultValue = "0", value = "page") Optional<Integer> page,
            @RequestParam(required = false, defaultValue = "0", value = "sortBy") Optional<String> sortBy) {
        return Response.ok(userResponseDtoService.findAllPaginated(
                PageRequest.of(
                        page.orElse(0),
                        5,
                        Sort.Direction.ASC,
                        sortBy.orElse("id"))));
    }

    @Operation(summary = "Получение пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @GetMapping("/{userId}")
    public Response<UserResponseDto> getUserById(@PathVariable Long userId) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findById(userId);
        ApiValidationUtil.requireTrue(userResponseDtoOptional.isPresent(), String.format("User by id %d not found", userId));
        return Response.ok(userResponseDtoOptional.get());
    }

    @Operation(summary = "Получение пользователя по username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @GetMapping("/username/{username}")
    public Response<UserResponseDto> getUserByUsername(@PathVariable String username) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByUsername(username);
        ApiValidationUtil.requireTrue(userResponseDtoOptional.isPresent(), String.format("User by username %s not found", username));
        return Response.ok(userResponseDtoOptional.get());
    }

    @Operation(summary = "Получение пользователя по email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @GetMapping("/email/{email}")
    public Response<UserResponseDto> getUserByEmail(@PathVariable String email) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByEmail(email);
        ApiValidationUtil.requireTrue(userResponseDtoOptional.isPresent(), String.format("User by email %s not found", email));
        return Response.ok(userResponseDtoOptional.get());
    }

    @Operation(summary = "Получение пользователя по phone")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно получен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @GetMapping("/phone/{phone}")
    public Response<UserResponseDto> getUserByPhone(@PathVariable String phone) {
        Optional<UserResponseDto> userResponseDtoOptional = userResponseDtoService.findByPhone(phone);
        ApiValidationUtil.requireTrue(userResponseDtoOptional.isPresent(), String.format("User by phone %s not found", phone));
        return Response.ok(userResponseDtoOptional.get());
    }

    @Operation(summary = "Удаление пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @DeleteMapping("/{userId}")
    public Response<Void> deleteUserById(@PathVariable Long userId) {
        ApiValidationUtil.requireTrue(userService.existsById(userId), String.format("User by id %d not found", userId));
        userService.deleteById(userId);
        return Response.ok();
    }

    @Operation(summary = "Добавление тренера пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренер успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @PutMapping("/{userId}/coach/{coachId}")
    public Response<UserResponseDto> updateUserCoach(@PathVariable Long userId,
                                                     @PathVariable Long coachId) {

        Optional<User> user = userService.findById(userId);
        ApiValidationUtil.requireTrue(user.isPresent(), String.format("User by id %d not found", userId));
        Optional<User> coach = userService.findByIdWithRoles(coachId);
        ApiValidationUtil.requireTrue(coach.isPresent(), String.format("Coach by id %d not found", coachId));
        ApiValidationUtil.requireTrue(coach.get().getRole().equals(RoleName.COACH),
                String.format("Coach by id %d not found", coachId));
        return Response.ok(UserMapper.toDto(userService.addCoach(user.get(), coach.get())));
    }

}
