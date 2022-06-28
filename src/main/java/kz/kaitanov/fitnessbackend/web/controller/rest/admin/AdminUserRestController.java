package kz.kaitanov.fitnessbackend.web.controller.rest.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.model.enums.RoleName;
import kz.kaitanov.fitnessbackend.service.interfaces.dto.UserResponseDtoService;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
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
import java.util.Optional;

@Tag(name = "AdminUserRestController", description = "CRUD  операции над пользователями")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/user")
public class AdminUserRestController {

    private final UserService userService;
    private final UserResponseDtoService userResponseDtoService;

    @Operation(summary = "Создание нового пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новый пользователь успешно создан")
    })
    @PostMapping
    public Response<UserResponseDto> saveUser(@RequestBody @Valid UserRegistrationRequestDto dto) {
        ApiValidationUtil.requireFalse(userService.existsByUsername(dto.username()), "username is being used by another user");
        ApiValidationUtil.requireFalse(dto.email() != null && userService.existsByEmail(dto.email()), "email is being used by another user");
        ApiValidationUtil.requireFalse(dto.phone() != null && userService.existsByPhone(dto.phone()), "phone is being used by another user");
        return Response.ok(UserMapper.toDto(userService.save(UserMapper.toEntity(dto))));
    }

    @Operation(summary = "Обновление существующего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Существующий пользователь успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @PutMapping
    public Response<UserResponseDto> updateUser(@RequestBody @Valid UserUpdateRequestDto dto) {
        ApiValidationUtil.requireTrue(userService.existsById(dto.id()),
                String.format("User by id %d not found", dto.id()));
        User user = UserMapper.updatePassword(UserMapper.toEntity(dto), new UserUpdatePasswordRequestDto(null));
        return Response.ok(UserMapper.toDto(userService.update(user)));
    }

    @Operation(summary = "Получение списка всех пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список всех пользователей успешно получен")
    })
    @GetMapping
    public Response<Page<UserResponseDto>> getUserPage(@PageableDefault(sort = "id") Pageable pageable) {
        return Response.ok(userResponseDtoService.findAll(pageable));
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
        ApiValidationUtil.requireTrue(coach.get().getRole().getName().equals(RoleName.COACH), String.format("Coach by id %d not found", coachId));
        return Response.ok(UserMapper.toDto(userService.addCoach(user.get(), coach.get())));
    }

    @Operation(summary = "Удаление тренера пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренер успешно удален"),
            @ApiResponse(responseCode = "400", description = "Тренер не найден")
    })
    @DeleteMapping ("/{userId}/coach")
    public Response<UserResponseDto> deleteUserCoach(@PathVariable Long userId) {
        Optional<User> user = userService.findById(userId);
        ApiValidationUtil.requireTrue(user.isPresent(), String.format("User by id %d not found", userId));
        ApiValidationUtil.requireTrue(user.get().getCoach() != null, String.format("User by id %d doesn't have a coach", userId));
        return Response.ok(UserMapper.toDto(userService.addCoach(user.get(), null)));
    }

    @Operation(summary = "Обновление пароля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль пользователя успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Пользователь не найден")
    })
    @PutMapping("/{userId}/password")
    public Response<UserResponseDto> updateUserPassword(@PathVariable Long userId,
                                                        @RequestBody @Valid UserUpdatePasswordRequestDto dto) {
        Optional<User> user = userService.findById(userId);
        ApiValidationUtil.requireTrue(user.isPresent(), String.format("User by id %d not found", userId));
        user.get().setPassword(dto.password());
        return Response.ok(UserMapper.toDto(userService.updatePassword(user.get())));
    }



}
