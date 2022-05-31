package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.exception.UserRegistrationException;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateEmailRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePasswordRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdatePhoneRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserUpdateProfileRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "UserRestController", description = "Операции обновления профиля пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/v1")
public class UserRestController {

    private final UserService userService;

    @Operation(summary = "Обновление профиля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль пользователя успешно обновлен")
    })
    @PutMapping("/profile")
    public ResponseEntity<UserResponseDto> updateUserProfile(@RequestBody @Valid UserUpdateProfileRequestDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(UserMapper.toDto(userService.update(UserMapper.updateProfile(user, dto))));
    }

    @Operation(summary = "Обновление пароля пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль пользователя успешно обновлен")
    })
    @PutMapping("/password")
    public ResponseEntity<UserResponseDto> updateUserPassword(@RequestBody @Valid UserUpdatePasswordRequestDto dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(UserMapper.toDto(userService.updatePassword(UserMapper.updatePassword(user, dto))));
    }

    @Operation(summary = "Обновление email пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email пользователя успешно обновлен")
    })
    @PutMapping("/email")
    public ResponseEntity<UserResponseDto> updateUserEmail(@RequestBody @Valid UserUpdateEmailRequestDto dto) {
        if (userService.existsByEmail(dto.email())) {
            throw new UserRegistrationException("email is being used by another user");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(UserMapper.toDto(userService.update(UserMapper.updateEmail(user, dto))));
    }

    @Operation(summary = "Обновление номера телефона пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Номера телефона пользователя успешно обновлен")
    })
    @PutMapping("/phone")
    public ResponseEntity<UserResponseDto> updateUserPhone(@RequestBody @Valid UserUpdatePhoneRequestDto dto) {
        if (userService.existsByPhone(dto.phone())) {
            throw new UserRegistrationException("phone is being used by another user");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(UserMapper.toDto(userService.update(UserMapper.updatePhone(user, dto))));
    }
}
