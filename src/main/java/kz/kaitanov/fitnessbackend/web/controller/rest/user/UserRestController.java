package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateEmailRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePhoneRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.api.Response;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import kz.kaitanov.fitnessbackend.web.config.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "UserRestController", description = "Контроллер для редактирования пользователем своего профиля")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "Эндпоинт для обновления профиля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль пользователя успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/profile")
    public Response<UserResponseDto> updateUserProfile(@RequestBody UserUpdateProfileRequestDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(UserMapper.toDto(userService.update(UserMapper.updateProfile(user, dto))));
    }

    @Operation(summary = "Эндпоинт для обновления пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль пользователя успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/password")
    public Response<UserResponseDto> updateUserPassword(@RequestBody @Valid UserUpdatePasswordRequestDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(UserMapper.toDto(userService.updatePassword(UserMapper.updatePassword(user, dto))));
    }

    @Operation(summary = "Эндпоинт для обновления почты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почта пользователя успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/email")
    public Response<UserResponseDto> updateUserEmail(@RequestBody @Valid UserUpdateEmailRequestDto dto) {
        ApiValidationUtil.requireFalse(userService.existsByEmail(dto.email()), "email is being used by another user");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(UserMapper.toDto(userService.update(UserMapper.updateEmail(user, dto))));
    }

    @Operation(summary = "Эндпоинт для обновление номера телефона")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Номера телефона пользователя успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Клиент допустил ошибки в запросе")
    })
    @PutMapping("/phone")
    public Response<UserResponseDto> updateUserPhone(@RequestBody @Valid UserUpdatePhoneRequestDto dto) {
        ApiValidationUtil.requireFalse(userService.existsByPhone(dto.phone()), "phone is being used by another user");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Response.ok(UserMapper.toDto(userService.update(UserMapper.updatePhone(user, dto))));
    }
}
