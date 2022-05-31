package kz.kaitanov.fitnessbackend.web.controller.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.kaitanov.fitnessbackend.exception.UserRegistrationException;
import kz.kaitanov.fitnessbackend.model.User;
import kz.kaitanov.fitnessbackend.model.converter.UserMapper;
import kz.kaitanov.fitnessbackend.model.dto.request.user.UserRegistrationRequestDto;
import kz.kaitanov.fitnessbackend.model.dto.response.UserResponseDto;
import kz.kaitanov.fitnessbackend.service.interfaces.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "UserRegistrationRestController", description = "Проверка данных при регистрации пользователя")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class UserRegistrationRestController {

    private final UserService userService;

    @Operation(summary = "Эндпоинт для регистрации пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация пользователя прошла успешно")
    })
    @PostMapping
    public ResponseEntity<UserResponseDto> registerNewUser(@RequestBody @Valid UserRegistrationRequestDto dto) {
        if (userService.existsByUsername(dto.username())) {
            throw new UserRegistrationException("username is being used by another user");
        }
        if (dto.email() != null && userService.existsByEmail(dto.email())) {
            throw new UserRegistrationException("email is being used by another user");
        }
        if (dto.phone() != null && userService.existsByPhone(dto.phone())) {
            throw new UserRegistrationException("phone is being used by another user");
        }
        User newUser = userService.save(UserMapper.toEntity(dto));
        return ResponseEntity.ok(UserMapper.toDto(newUser));
    }
}
